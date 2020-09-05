package ru.endroad.libraries.camp

import androidx.annotation.LayoutRes

interface Screen {

	@get:LayoutRes
	val layout: Int
}