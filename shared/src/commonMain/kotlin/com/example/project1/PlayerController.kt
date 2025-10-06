package com.example.project1


interface PlayerController {
    fun play()
    fun pause()
    fun stop()
    fun seek(positionMs: Long)
    fun getState(): PlayerState
}

data class PlayerState(
    val isPlaying: Boolean,
    val currentPositionMs: Long,
    val durationMs: Long
)

expect fun createPlayer(url: String): PlayerController


