package com.example.project1

import kotlinx.browser.document
import org.w3c.dom.HTMLAudioElement

class JsPlayer(private val url: String) : PlayerController {
    private val audio: HTMLAudioElement = (document.createElement("audio") as HTMLAudioElement).apply {
        src = url
        preload = "auto"
    }

    override fun play() {
        console.log("🎵 Pokušavam da pustim audio: $url")
        val promise = audio.play()
        if (promise != null) {
            promise
                .then { console.log("✅ Audio pušten!") }
                .catch { err -> console.log("❌ Greška pri puštanju: ${err.message}") }
        } else {
            console.log("⚠️ Nema Promise-a, možda audio nije učitan?")
        }
    }

    override fun pause() {
        audio.pause()
    }

    override fun stop() {
        audio.pause()
        audio.currentTime = 0.0
    }

    override fun seek(positionMs: Long) {
        audio.currentTime = positionMs / 1000.0
    }


    override fun getState(): PlayerState {
        val isPlaying = !audio.paused
        val current = (audio.currentTime * 1000).toLong()
        val duration = if (audio.duration.isFinite()) (audio.duration * 1000).toLong() else 1L
        return PlayerState(isPlaying, current, duration)
    }
}



actual fun createPlayer(url: String): PlayerController = JsPlayer(url)