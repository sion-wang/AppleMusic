package com.sion.itunes.model.vo

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(
    val resultCount: Int = 0,
    val results: List<Music> = arrayListOf<Music>()
)