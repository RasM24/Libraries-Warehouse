package ru.endroad.libraries.camp.extension

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.IntDef
import androidx.annotation.IntRange
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Context.showAlertSimple(title: String? = null, message: String, click: () -> Unit) {
	AlertDialog.Builder(this).apply {
		title?.let(::setTitle)
		setMessage(message)
		setCancelable(false)
		setPositiveButton("ОК") { _, _ -> click() }
	}
		.create()
		.show()
}

fun Fragment.showAlertSimple(title: String? = null, message: String, click: () -> Unit) {
	AlertDialog.Builder(context).apply {
		title?.let(::setTitle)
		setMessage(message)
		setCancelable(false)
		setPositiveButton("ОК") { _, _ -> click() }
	}
		.create()
		.show()
}

@IntDef(Toast.LENGTH_SHORT, Toast.LENGTH_LONG)
@Retention(AnnotationRetention.SOURCE)
annotation class ToastLength

fun Context.showToast(message: String, @ToastLength duration: Int = Toast.LENGTH_SHORT) =
	Toast.makeText(this, message, duration).show()

fun Context.showToast(@StringRes messageId: Int, @ToastLength duration: Int = Toast.LENGTH_SHORT) =
	Toast.makeText(this, messageId, duration).show()

fun Fragment.showToast(message: String, @ToastLength duration: Int = Toast.LENGTH_SHORT) =
	Toast.makeText(context, message, duration).show()

fun Fragment.showToast(@StringRes messageId: Int, @ToastLength duration: Int = Toast.LENGTH_SHORT) =
	Toast.makeText(context, messageId, duration).show()

@IntRange(from = 1)
@IntDef(Snackbar.LENGTH_SHORT, Snackbar.LENGTH_LONG, Snackbar.LENGTH_INDEFINITE)
@Retention(AnnotationRetention.SOURCE)
annotation class SnackLength

fun Context.showSnack(rootView: View, message: String, @SnackLength duration: Int = Snackbar.LENGTH_SHORT) =
	Snackbar.make(rootView, message, duration).show()

fun Context.showSnack(rootView: View, @StringRes messageId: Int, @SnackLength duration: Int = Snackbar.LENGTH_SHORT) =
	Snackbar.make(rootView, messageId, duration).show()

fun Fragment.showSnack(rootView: View, message: String, @SnackLength duration: Int = Snackbar.LENGTH_SHORT) =
	Snackbar.make(rootView, message, duration).show()

fun Fragment.showSnack(rootView: View, @StringRes messageId: Int, @SnackLength duration: Int = Snackbar.LENGTH_SHORT) =
	Snackbar.make(rootView, messageId, duration).show()