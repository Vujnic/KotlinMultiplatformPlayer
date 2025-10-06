package com.example.project1

import kotlinx.cinterop.CValue
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.cValue
import platform.AVFoundation.*
import platform.CoreMedia.*
import platform.Foundation.NSURL
import platform.Foundation.timeIntervalSince1970

class IosPlayer(private val url: String) : PlayerController {

    private val nsUrl = NSURL.URLWithString(url) ?: error("Invalid URL: $url")
    private val player = AVPlayer.playerWithURL(nsUrl)

    override fun play() {
        player.play()
    }

    override fun pause() {
        player.pause()
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun stop() {
        player.pause()

        val zeroTime: CValue<CMTime> = cValue {
            value = 0
            timescale = 1
            flags = 1u
            epoch = 0
        }

        player.seekToTime(zeroTime) { _ -> }
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun seek(positionMs: Long) {
        val seconds = positionMs / 1000.0

        val time: CValue<CMTime> = cValue {
            value = (seconds * 600).toLong()
            timescale = 600
            flags = 1u
            epoch = 0
        }

        player.seekToTime(time) { _ -> }
    }

    @OptIn(ExperimentalForeignApi::class)
    override fun getState(): PlayerState {
        val item = player.currentItem

        // Ako currentDate nije dostupna, koristi 0
        val currentTimeSeconds = item?.currentDate()?.timeIntervalSince1970() ?: 0.0

        val duration = item?.duration ?: cValue {
            value = 0
            timescale = 1
            flags = 1u
            epoch = 0
        }

        val durationSeconds = CMTimeGetSeconds(duration)
        val isPlaying = player.rate != 0f

        return PlayerState(
            isPlaying = isPlaying,
            currentPositionMs = (currentTimeSeconds * 1000).toLong(),
            durationMs = if (!durationSeconds.isNaN()) (durationSeconds * 1000).toLong() else 1L
        )
    }
}


actual fun createPlayer(url: String): PlayerController = IosPlayer(url)