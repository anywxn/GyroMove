package com.example.gyromove.game

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.gyromove.R
import com.example.gyromove.sensors.TiltController

class GameView : SurfaceView, SurfaceHolder.Callback {

    // Главный конструктор, который ты используешь в коде
    constructor(context: Context, tiltController: TiltController) : super(context) {
        this.tiltController = tiltController
        initView()
    }

    // Дополнительные конструкторы для Android Studio (чтобы не ругалась)
    constructor(context: Context) : this(context, TiltController(context))
    constructor(context: Context, attrs: AttributeSet?) : this(context, TiltController(context))
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this(context, TiltController(context))

    private lateinit var tiltController: TiltController
    private var gameThread: GameThread? = null
    private lateinit var player: Player
    private lateinit var backgroundBitmap: Bitmap

    private fun initView() {
        holder.addCallback(this)
        // Загружаем фон сразу
        backgroundBitmap = BitmapFactory.decodeResource(resources, R.drawable.grass)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        // Создаём игрока (позиция может зависеть от размеров экрана, но пока фиксированно)
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
        // Рисуем фон на весь экран
        canvas.drawBitmap(backgroundBitmap, null, Rect(0, 0, width, height), null)
        // Рисуем игрока
        player.draw(canvas)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        // пока ничего не делаем
    }
}