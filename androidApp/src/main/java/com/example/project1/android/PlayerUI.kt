package com.example.project1.android

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.project1.PlayerController
import kotlinx.coroutines.delay

@Composable
fun PlayerUI(player: PlayerController) {
    var state by remember { mutableStateOf(player.getState()) }

    LaunchedEffect(Unit) {
        while (true) {
            state = player.getState()
            delay(500L)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🎧", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(24.dp))

        val maxRange = if (state.durationMs > 0) state.durationMs.toFloat() else 1f

        Slider(
            value = state.currentPositionMs.toFloat().coerceIn(0f, maxRange),
            onValueChange = { newValue ->
                player.seek(newValue.toLong())
            },
            valueRange = 0f..maxRange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(horizontalArrangement = Arrangement.Center) {
            Button(onClick = {
                if (state.isPlaying) player.pause() else player.play()
            }) {
                Text(if (state.isPlaying) "Pause" else "Play")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(onClick = {
                player.stop()
            }) {
                Text("Stop")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "${state.currentPositionMs / 1000}s / ${state.durationMs / 1000}s",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}