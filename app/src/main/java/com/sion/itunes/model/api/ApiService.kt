package com.sion.itunes.model.api

import com.sion.itunes.model.vo.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/search")
    suspend fun search(
        @Query("term") term: String = "",
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 20
    ): Response<SearchResponse>
}