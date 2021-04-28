package com.sion.itunes.model.vo

data class SearchResponse(
    val resultCount: Int = 0,
    val results: List<Music> = arrayListOf()
)