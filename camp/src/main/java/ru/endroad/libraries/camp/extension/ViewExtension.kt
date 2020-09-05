package ru.endroad.libraries.camp.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View

fun showViews(vararg views: View) = views.forEach { it.visibility = View.VISIBLE }

fun hideViews(vararg views: View) = views.forEach { it.visibility = View.GONE }

fun View.show() {
	visibility = View.VISIBLE
}

fun View.hide() {
	visibility = View.GONE
}

fun enableViews(vararg views: View) = views.forEach { it.isEnabled = true }

fun disableViews(vararg views: View) = views.forEach()  { it.isEnabled = false }

fun View.enable() {
	isEnabled = true
}

fun View.disable() {
	isEnabled = false
}

fun Context.launchActivity(activity: Class<out Activity>) {
	startActivity(Intent(this, activity))
}