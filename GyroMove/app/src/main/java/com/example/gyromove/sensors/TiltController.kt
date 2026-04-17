package com.example.gyromove.sensors

import android.content.Context
import android.hardware.Sensor
import android.hardware.Sensor.TYPE_ACCELEROMETER
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
class TiltController(context: Context) : SensorEventListener {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val accelerometer =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    var tiltX = 0f
        private set

    var tiltY = 0f
        private set

    private val alpha = 0.1f

    override fun onSensorChanged(event: SensorEvent) {
        val rawX = event.values[0]
        val rawY = event.values[1]

        tiltX = tiltX + alpha * (rawX - tiltX)
        tiltY = tiltY + alpha * (rawY - tiltY)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // пусто
    }

    fun start() {
        sensorManager.registerListener(
            this,
            accelerometer,
            SensorManager.SENSOR_DELAY_GAME
        )
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }
}