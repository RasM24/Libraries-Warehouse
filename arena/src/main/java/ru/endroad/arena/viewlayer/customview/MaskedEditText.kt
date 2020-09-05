package ru.endroad.arena.viewlayer.customview

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import ru.endroad.libraries.arena.R

class MaskedEditText @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs), TextWatcher {

	private val mask: String
	private val charRepresentation: Char
	private val allowedChars: String?
	private val rawToMask: IntArray
	private val maskToRaw: IntArray
	private val rawText: RawString = RawString()

	private var editingBefore: Boolean = false
	private var editingOnChanged: Boolean = false
	private var editingAfter: Boolean = false
	private var selection1: Int = 0
	private var initialized: Boolean = false
	private var ignore: Boolean = false
	protected val maxRawLength: Int
	private val lastValidMaskPosition: Int
	private var selectionChanged: Boolean = false
	private var focusChangeListener: OnFocusChangeListener? = null

	init {
		addTextChangedListener(this)

		context.obtainStyledAttributes(attrs, R.styleable.MaskedEditText).apply {

			mask = getString(R.styleable.MaskedEditText_mask) ?: throw RuntimeException("")
			charRepresentation = getString(R.styleable.MaskedEditText_char_representation)?.get(0) ?: '#'
			allowedChars = getString(R.styleable.MaskedEditText_allowed_chars)

			/**
			 * Generates positions for values characters. For instance:
			 * Input data: mask = "+7(###)###-##-##
			 * After method execution:
			 * rawToMask = [3, 4, 5, 6, 8, 9, 11, 12, 14, 15]
			 * maskToRaw = [-1, -1, -1, 0, 1, 2, -1, 3, 4, 5, -1, 6, 7, -1, 8, 9]
			 * charsInMask = "+7()- " (and space, yes)
			 */
			val aux = IntArray(mask.length)
			maskToRaw = IntArray(mask.length)
			var charsInMaskAux = ""

			var charIndex = 0
			for (i in mask.indices) {
				val currentChar = mask[i]
				if (currentChar == charRepresentation) {
					aux[charIndex] = i
					maskToRaw[i] = charIndex++
				} else {
					val charAsString = currentChar.toString()
					if (!charsInMaskAux.contains(charAsString)) {
						charsInMaskAux += charAsString
					}
					maskToRaw[i] = -1
				}
			}
			if (charsInMaskAux.indexOf(' ') < 0) {
				charsInMaskAux += SPACE
			}

			charsInMaskAux.toCharArray()

			rawToMask = IntArray(charIndex)
			for (i in 0 until charIndex) {
				rawToMask[i] = aux[i]
			}

			selection1 = rawToMask[0]

			editingBefore = true
			editingOnChanged = true
			editingAfter = true
			if (hasHint() && rawText.length == 0) {
				setText(makeMaskedTextWithHint())
			} else {
				setText(makeMaskedText())
			}
			editingBefore = false
			editingOnChanged = false
			editingAfter = false

			maxRawLength = maskToRaw[previousValidPosition(mask.length - 1)] + 1
			lastValidMaskPosition = findLastValidMaskPosition()
			initialized = true

			super.setOnFocusChangeListener { v, hasFocus ->
				focusChangeListener?.onFocusChange(v, hasFocus)

				if (hasFocus()) {
					selectionChanged = false
					this@MaskedEditText.setSelection(lastValidPosition())
				}
			}


			recycle()
		}

	}

	override fun onSaveInstanceState(): Parcelable? {
		val superParcellable = super.onSaveInstanceState()
		val state = Bundle()
		state.putParcelable("super", superParcellable)
		state.putString("text", getRawText())
		return state
	}

	override fun onRestoreInstanceState(state: Parcelable) {
		val bundle = state as Bundle
		super.onRestoreInstanceState(state.getParcelable("super"))
		val text = bundle.getString("text")

		setText(text)
	}

	/** @param listener - its onFocusChange() method will be called before performing MaskedEditText operations,
	 * related to this event.
	 */
	override fun setOnFocusChangeListener(listener: OnFocusChangeListener) {
		focusChangeListener = listener
	}

	private fun findLastValidMaskPosition(): Int {
		for (i in maskToRaw.indices.reversed()) {
			if (maskToRaw[i] != -1) return i
		}
		throw RuntimeException("Mask must contain at least one representation char")
	}

	private fun hasHint(): Boolean =
		hint != null

	fun getRawText(): String =
		this.rawText.text

	override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
		if (!editingBefore) {
			editingBefore = true
			if (start > lastValidMaskPosition) {
				ignore = true
			}
			var rangeStart = start
			if (after == 0) {
				rangeStart = erasingStart(start)
			}
			val range = calculateRange(rangeStart, start + count)
			if (range.start != -1) {
				rawText.subtractFromString(range)
			}
			if (count > 0) {
				selection1 = previousValidPosition(start)
			}
		}
	}

	private fun erasingStart(start: Int): Int {
		var start1 = start
		while (start1 > 0 && maskToRaw[start1] == -1) {
			start1--
		}
		return start1
	}

	override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
		var count1 = count
		if (!editingOnChanged && editingBefore) {
			editingOnChanged = true
			if (ignore) {
				return
			}
			if (count1 > 0) {
				val startingPosition = maskToRaw[nextValidPosition(start)]
				val addedString = s.subSequence(start, start + count1).toString()
				count1 = rawText.addToString(addedString.cleared(), startingPosition, maxRawLength)
				val currentPosition: Int = if (startingPosition + count1 < rawToMask.size)
					rawToMask[startingPosition + count1]
				else
					lastValidMaskPosition + 1
				selection1 = nextValidPosition(currentPosition)
			}
		}
	}

	override fun afterTextChanged(s: Editable) {
		if (!editingAfter && editingBefore && editingOnChanged) {
			editingAfter = true
			if (hasHint() && (rawText.length == 0)) {
				setText(makeMaskedTextWithHint())
			} else {
				setText(makeMaskedText())
			}

			selectionChanged = false
			setSelection(selection1)

			editingBefore = false
			editingOnChanged = false
			editingAfter = false
			ignore = false
		}
	}

	override fun onSelectionChanged(selStart: Int, selEnd: Int) {
		var startSelection = selStart
		var endSelection = selEnd
		// On Android 4+ this method is being called more than 1 time if there is a hint in the EditText, what moves the cursor to left
		// Using the boolean var selectionChanged to limit to one execution

		if (initialized) {
			if (!selectionChanged) {
				startSelection = fixSelection(startSelection)
				endSelection = fixSelection(endSelection)

				// exactly in this order. If getText.length() == 0 then selStart will be -1
				if (startSelection > text!!.length) startSelection = text!!.length
				if (startSelection < 0) startSelection = 0

				// exactly in this order. If getText.length() == 0 then selEnd will be -1
				if (endSelection > text!!.length) endSelection = text!!.length
				if (endSelection < 0) endSelection = 0

				setSelection(startSelection, endSelection)
				selectionChanged = true
			} else {
				//check to see if the current selection1 is outside the already entered text
				if (startSelection > rawText.length - 1) {
					val start = fixSelection(startSelection)
					val end = fixSelection(endSelection)
					if (start >= 0 && end < text!!.length) {
						setSelection(start, end)
					}
				}
			}
		}
		super.onSelectionChanged(startSelection, endSelection)
	}

	private fun fixSelection(selection: Int): Int =
		if (selection > lastValidPosition()) {
			lastValidPosition()
		} else {
			nextValidPosition(selection)
		}

	private fun nextValidPosition(currentPosition: Int): Int {
		var position = currentPosition
		while (position < lastValidMaskPosition && maskToRaw[position] == -1) {
			position++
		}
		return if (position > lastValidMaskPosition) lastValidMaskPosition + 1 else position
	}

	private fun previousValidPosition(currentPosition: Int): Int {
		var position = currentPosition
		while (position >= 0 && maskToRaw[position] == -1) {
			position--
			if (position < 0) {
				return nextValidPosition(0)
			}
		}
		return position
	}

	private fun lastValidPosition(): Int =
		if (rawText.length == maxRawLength) {
			rawToMask[rawText.length - 1] + 1
		} else nextValidPosition(rawToMask[rawText.length])

	private fun makeMaskedText(): String {
		val maskedTextLength: Int = if (rawText.length < rawToMask.size) {
			rawToMask[rawText.length]
		} else {
			mask.length
		}
		val maskedText = CharArray(maskedTextLength) //mask.replace(charRepresentation, ' ').toCharArray();
		for (i in maskedText.indices) {
			val rawIndex = maskToRaw[i]
			if (rawIndex == -1) {
				maskedText[i] = mask[i]
			} else {
				maskedText[i] = rawText[rawIndex]
			}
		}
		return String(maskedText)
	}

	private fun makeMaskedTextWithHint(): CharSequence {
		val ssb = SpannableStringBuilder()
		var mtrv: Int
		val maskFirstChunkEnd = rawToMask[0]
		for (i in mask.indices) {
			mtrv = maskToRaw[i]
			if (mtrv != -1) {
				if (mtrv < rawText.length) {
					ssb.append(rawText[mtrv])
				} else {
					ssb.append(hint[maskToRaw[i]])
				}
			} else {
				ssb.append(mask[i])
			}
			if (rawText.length < rawToMask.size && i >= rawToMask[rawText.length] || i >= maskFirstChunkEnd) {
				ssb.setSpan(ForegroundColorSpan(currentHintTextColor), i, i + 1, 0)
			}
		}
		return ssb
	}

	private fun calculateRange(start: Int, end: Int): Range {
		val range = Range()
		var i = start
		while (i <= end && i < mask.length) {
			if (maskToRaw[i] != -1) {
				if (range.start == -1) {
					range.start = maskToRaw[i]
				}
				range.end = maskToRaw[i]
			}
			i++
		}
		if (end == mask.length) {
			range.end = rawText.length
		}
		if (range.start == range.end && start < end) {
			val newStart = previousValidPosition(range.start - 1)
			if (newStart < range.start) {
				range.start = newStart
			}
		}
		return range
	}

	private fun String.cleared(): String {
		allowedChars ?: return this

		val builder = StringBuilder(length)

		toCharArray()
			.asList()
			.filter { allowedChars.contains(it) }
			.map(builder::append)

		return builder.toString()
	}

	companion object {

		const val SPACE = " "
	}
}