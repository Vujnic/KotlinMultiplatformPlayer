package com.example.project1

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLButtonElement
import org.w3c.dom.HTMLInputElement

fun main() {
    val player = createPlayer("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3")

    val playButton = document.getElementById("playBtn") as HTMLButtonElement
    val pauseButton = document.getElementById("pauseBtn") as HTMLButtonElement
    val stopButton = document.getElementById("stopBtn") as HTMLButtonElement
    val slider = document.getElementById("progress") as HTMLInputElement

    playButton.onclick = { player.play(); null }
    pauseButton.onclick = { player.pause(); null }
    stopButton.onclick = {
        player.stop()
        slider.value = "0"
        null
    }

    slider.oninput = {
        val newValue = slider.value.toLong()
        player.seek(newValue)
        null
    }

    window.setInterval({
        val state = player.getState()
        if (state.durationMs > 0) {
            slider.max = state.durationMs.toString()
            if (!slider.matches(":active")) {
                slider.value = state.currentPositionMs.toString()
            }
        }
    }, 500)

    val timeText = document.getElementById("time")

    window.setInterval({
        val state = player.getState()
        if (state.durationMs > 0) {
            slider.max = state.durationMs.toString()
            if (!slider.matches(":active")) {
                slider.value = state.currentPositionMs.toString()
            }

            val currentSec = state.currentPositionMs / 1000
            val totalSec = state.durationMs / 1000
            timeText?.textContent = "Position: ${currentSec}s / ${totalSec}s"
        }
    }, 500)
}