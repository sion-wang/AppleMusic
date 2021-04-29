package com.sion.itunes.model.api

class ApiRepository(private val apiService: ApiService) {

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

    suspend fun search(term: String, offset: Int = 0, limit: Int = NETWORK_PAGE_SIZE) =
        apiService.search(term, offset, limit)
}