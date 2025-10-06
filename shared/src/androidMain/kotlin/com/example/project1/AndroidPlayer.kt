package com.example.project1

import android.media.MediaPlayer

class AndroidPlayer(private val url: String) : PlayerController {
    private val player = MediaPlayer().apply {
        setOnPreparedListener {
            it.start()
        }
        setOnCompletionListener {
            it.seekTo(0)
        }
    }

    private var isPrepared = false

    override fun play() {
        try {
            if (!isPrepared) {
                player.reset()
                player.setDataSource(url)
                player.prepareAsync()
                player.setOnPreparedListener {
                    isPrepared = true
                    it.start()
                }
            } else {
                player.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun pause() {
        if (player.isPlaying) player.pause()
    }

    override fun stop() {
        player.stop()
        isPrepared = false
    }

    override fun seek(positionMs: Long) {
        try {
            if (isPrepared) player.seekTo(positionMs.toInt())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getState(): PlayerState {
        return PlayerState(
            isPlaying = player.isPlaying,
            currentPositionMs = if (isPrepared) player.currentPosition.toLong() else 0L,
            durationMs = if (isPrepared) player.duration.toLong() else 0L
        )
    }
}

actual fun createPlayer(url: String): PlayerController = AndroidPlayer(url)