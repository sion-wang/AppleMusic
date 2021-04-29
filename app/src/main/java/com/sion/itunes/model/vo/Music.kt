package com.sion.itunes.model.vo

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "musics")
data class Music(
    @PrimaryKey
    val trackId: Long = -1,
    val artistName: String = "",
    val trackName: String = "",
    val artworkUrl100: String = "",
    val previewUrl: String = "",
    var keyword: String = "",
    var insertIndex: Long = 0
)
