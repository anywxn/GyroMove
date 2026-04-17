package com.example.gyromove.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.gyromove.sensors.TiltController

class GameView( context: Context, private val tiltController: TiltController) : SurfaceView(context), SurfaceHolder.Callback {

    private var gameThread: GameThread? = null
    private lateinit var player: Player

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        player = Player(166f, 335f, context)
        gameThread = GameThread(holder, this)
        gameThread?.running = true
        gameThread?.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        pause()
    }

    fun resume() {
        if (gameThread == null) {
            gameThread = GameThread(holder, this)
            gameThread?.running = true
            gameThread?.start()
        }
    }

    fun pause() {
        gameThread?.running = false
        gameThread?.join()
        gameThread = null
    }

    fun update() {
        val tiltX = tiltController.tiltX
        val tiltY = tiltController.tiltY

        player.update(tiltX, tiltY, width, height)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        canvas.drawColor(Color.WHITE)
        player.draw(canvas)
    }
    override fun surfaceChanged(
        holder: SurfaceHolder,
        format: Int,
        width: Int,
        height: Int
    ) {
        // пока ничего не делаем
    }
}