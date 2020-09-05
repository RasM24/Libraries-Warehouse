package ru.endroad.arena.data

import android.graphics.*
import com.squareup.picasso.Transformation

/**
 * Трансформирует картинку в круглую
 *
 * обрезает квадрат, привязываясь к верхней грани
 */
class CircleTransform : Transformation {

	override fun transform(source: Bitmap): Bitmap {

		//crop square
		val size = source.width.coerceAtMost(source.height)
		val squaredBitmap = Bitmap.createBitmap(source, 0, 0, size, size)

		if (squaredBitmap != source)
			source.recycle()

		//crop circle
		val bitmap = Bitmap.createBitmap(size, size, source.config)
		val canvas = Canvas(bitmap)

		val shader = BitmapShader(squaredBitmap,
								  Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
		val paint = Paint()
		paint.shader = shader
		paint.isAntiAlias = true
		val r = size / 2f
		canvas.drawCircle(r, r, r, paint)

		squaredBitmap.recycle()
		return bitmap
	}

	override fun key() = "circle"

}