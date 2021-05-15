package com.sion.itunes.model.api.search

import com.sion.itunes.model.vo.SearchResponse
import retrofit2.Response

class FakeSearchApiRepository(private val apiService: SearchApiService): ISearchApiRepository {
    override suspend fun search(term: String, offset: Int, limit: Int): Response<SearchResponse> {
        return apiService.search(term, offset, limit)
    }
}