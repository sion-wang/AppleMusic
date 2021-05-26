package com.sion.itunes.model.vo

import androidx.room.Entity
import androidx.room.PrimaryKey
//import kotlinx.serialization.SerialName
//import kotlinx.serialization.Serializable

@Entity(tableName = "musics")
data class Music(
    @PrimaryKey
//    @SerialName("trackId")
    val trackId: Long = -1,
//    @SerialName("artistName")
    val artistName: String = "",
//    @SerialName("trackName")
    val trackName: String = "",
//    @SerialName("rtworkUrl100")
    val artworkUrl100: String = "",
//    @SerialName("previewUrl")
    val previewUrl: String = "",
//    @SerialName("keyword")
    var keyword: String = "",
//    @SerialName("insertIndex")
    var insertIndex: Long = 0
)
