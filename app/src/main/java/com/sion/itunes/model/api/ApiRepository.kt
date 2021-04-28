package com.sion.itunes.model.api

import com.sion.itunes.model.vo.GithubUser
import com.sion.itunes.model.vo.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import retrofit2.Response

class ApiRepository(private val apiService: ApiService) {

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

    suspend fun getUsers(since: Int, perPage: Int = NETWORK_PAGE_SIZE): Response<List<GithubUser>> {
        return apiService.getUsers(since, perPage)
    }

    suspend fun getUserByName(name: String): Flow<Response<GithubUser>> =
        flowOf(apiService.getUserByName(name))
            .flowOn(Dispatchers.IO)
            .map {
                if (it.isSuccessful) {
                    return@map it
                } else {
                    throw HttpException(it)
                }
            }

    suspend fun search(term: String, offset: Int = 0, limit: Int = NETWORK_PAGE_SIZE) =
        apiService.search(term, offset, limit)
}