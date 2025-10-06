package com.example.project1.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.project1.createPlayer

class MainActivity : ComponentActivity() {
    private val url = "https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3"
    private val player = createPlayer(url)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlayerUI(player)
        }
    }
}
