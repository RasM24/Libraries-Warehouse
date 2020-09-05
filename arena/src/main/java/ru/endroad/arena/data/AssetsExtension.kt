package ru.endroad.arena.data

import android.content.res.AssetManager
import android.graphics.drawable.Drawable

fun AssetManager.openString(name: String): String? =
	runCatching {
		open(name).use {
			val buffer = ByteArray(it.available())
			it.read(buffer)
			String(buffer)
		}
	}.getOrNull()

fun AssetManager.openImage(path: String): Drawable? =
	runCatching { Drawable.createFromStream(open(path), null) }
		.getOrNull()