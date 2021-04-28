package com.sion.itunes.model.api

import com.sion.itunes.model.vo.GithubUser
import com.sion.itunes.model.vo.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/users")
    suspend fun getUsers(
        @Query("since") since: Int = 0,
        @Query("per_page") per_page: Int = 20
    ): Response<List<GithubUser>>

    @GET("/user/{username}")
    suspend fun getUserByName(
        @Path("username") username: String = "",
    ): Response<GithubUser>

    @GET("/search")
    suspend fun search(
        @Query("term") term: String = "",
        @Query("offset") offset: Int = 0,
        @Query("limit") limit: Int = 20
    ): Response<SearchResponse>
}