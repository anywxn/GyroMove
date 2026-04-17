package com.example.gyromove.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.gyromove.R
import androidx.core.graphics.scale

class Player(
    var x: Float,
    var y: Float,
    context: Context,
    var tiltX: Float = 0f,
    var tiltY: Float = 0f,
) {
    private val bitmap: Bitmap =
        BitmapFactory.decodeResource(context.resources, R.drawable.player).scale(166, 335)

    private val radius = 50f
    private val speed = 5f

    private val paint = Paint().apply {
        color = Color.BLUE
    }

    fun update(tiltX: Float, tiltY: Float, screenW: Int, screenH: Int) {

        this.tiltX = tiltX
        this.tiltY = tiltY
        // движение
        x -= tiltX * speed
        y += tiltY * speed

        // ограничение по экрану
        if (x < radius) x = radius
        if (x > screenW - radius) x = screenW - radius
        if (y < radius) y = radius
        if (y > screenH - radius) y = screenH - radius
    }

    fun draw(canvas: Canvas) {
        canvas.save()
        canvas.rotate(tiltX * 5, x, y)
        canvas.drawBitmap(
            bitmap,
            x - bitmap.width / 2,
            y - bitmap.height / 2,
            null,
        )
        canvas.restore()
    }

}