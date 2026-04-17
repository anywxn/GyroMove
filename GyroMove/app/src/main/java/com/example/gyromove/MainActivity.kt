package com.example.gyromove

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.gyromove.game.GameView
import com.example.gyromove.sensors.TiltController
import com.example.gyromove.ui.theme.GyroMoveTheme
import android.content.pm.ActivityInfo

class MainActivity : ComponentActivity() {
    private lateinit var gameView: GameView
    private lateinit var tiltController: TiltController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        tiltController = TiltController(this)
        gameView = GameView(this, tiltController)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val contentView = setContentView(gameView)
    }

    override fun onResume() {
        super.onResume()
        tiltController.start()
        gameView.resume()
    }

    override fun onPause() {
        super.onPause()
        tiltController.stop()
        gameView.pause()
    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GyroMoveTheme {
        Greeting("Android")
    }
}