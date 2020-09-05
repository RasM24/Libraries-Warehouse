package ru.endroad.libraries.camp.activity

import android.os.Bundle
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatActivity
import ru.endroad.libraries.camp.Screen

abstract class CampActivity : AppCompatActivity(), Screen {

	@StyleRes
	open val theme: Int? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		theme?.let(::setTheme)
		setContentView(layout)

		savedInstanceState ?: onFirstCreate()
	}

	open fun onFirstCreate() = Unit
}