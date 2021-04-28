package com.sion.itunes.model.vo

data class Music(
    val trackId: Long = -1,
    val artistName: String = "",
    val trackName: String = "",
    val artworkUrl100: String = "",
    val previewUrl: String = ""
)
