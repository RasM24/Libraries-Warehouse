package ru.endroad.arena.data

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.annotation.DrawableRes
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

//region start with Intent

/**
 * Запуск браузера по url
 */
fun Context.startUrl(url: String) = startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))

/**
 * Запуск почтового приложения
 */
fun Context.startEmail(email: String) =
	startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email")))

/**
 * Запуск звонилки с данным номером
 */
fun Context.startPhone(number: String) =
	startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number")))

/**
 * Поделиться информацией
 */
fun Context.share(text: String) =
	startActivity(Intent().apply {
		action = Intent.ACTION_SEND
		putExtra(Intent.EXTRA_TEXT, text)
		type = "text/plain"
	})

//endregion

//region Работа со шрифтами

/**
 * Установка шрифта в TextView
 */
var TextView.font: String
	get() = ""
	set(value) {
		this.typeface = Typeface.createFromAsset(context.assets, value)
	}

/**
 * Костыль для изменения шрифта в toolbar
 */
fun Toolbar.changeFont(font: String) {
	for (i in 0 until childCount) {
		val view = getChildAt(i)
		if (view is TextView && view.text == title)
			view.font = font
		break
	}
}
//endregion

val picasso: Picasso
	get() = Picasso.get()

fun ImageView.load(path: String) {
	if (path.isEmpty()) return

	picasso
		.load(path)
		.fit()
		.centerCrop()
		.into(this)
}

/**
 * Загрузка изображений с помощью Picasso
 */
fun ImageView.load(path: String, transform: Transformation) {
	if (path.isEmpty()) return

	picasso
		.load(path)
		.fit()
		.centerCrop()
		.transform(transform)
		.into(this)
}

fun ImageView.load(@DrawableRes drawableId: Int) {
	picasso
		.load(drawableId)
		.fit()
		.centerCrop()
		.into(this)
}

/**
 * Загрузка изображений с помощью Picasso
 */
fun ImageView.load(@DrawableRes drawableId: Int, transform: Transformation) {
	picasso
		.load(drawableId)
		.fit()
		.centerCrop()
		.transform(transform)
		.into(this)
}