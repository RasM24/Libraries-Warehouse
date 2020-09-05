package ru.endroad.arena.data.converter

import android.graphics.*
import android.media.Image
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer

typealias ImageBase64 = String

val ImageBase64.asBitmap: Bitmap
	get() {
		val bytes = Base64.decode(this, Base64.DEFAULT)
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
	}

val Bitmap.toBase64: ImageBase64
	get() {
		TODO()
	}

//TODO добавить проверку, что Image действительно является image и конвертацию можно произвести
val Image.asJPEG: Bitmap
	get() {
		val buffer: ByteBuffer = this.planes[0].buffer
		val bytes = ByteArray(buffer.remaining())
		buffer.get(bytes)

		return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
	}

val Image.asYUV: YuvImage
	get() {
		val yBuffer = this.planes[0].buffer
		val uBuffer = this.planes[1].buffer
		val vBuffer = this.planes[2].buffer

		val ySize = yBuffer.remaining()
		val uSize = uBuffer.remaining()
		val vSize = vBuffer.remaining()
		val byteArrayNV21 = ByteArray(ySize + uSize + vSize)
		yBuffer.get(byteArrayNV21, 0, ySize)
		vBuffer.get(byteArrayNV21, ySize, vSize)
		uBuffer.get(byteArrayNV21, ySize + vSize, uSize)

		return YuvImage(byteArrayNV21, ImageFormat.NV21, this.width, this.height, null)
	}

val YuvImage.asBitmap: Bitmap
	get() {
		val imageBytes = ByteArrayOutputStream().also {
			compressToJpeg(Rect(0, 0, this.width, this.height), 100, it)
		}.toByteArray()

		return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
	}