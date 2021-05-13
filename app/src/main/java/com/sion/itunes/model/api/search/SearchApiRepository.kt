package com.sion.itunes.model.api.search

class SearchApiRepository(private val searchApiService: SearchApiService) :
    ISearchApiRepository {

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

    override suspend fun search(term: String, offset: Int, limit: Int) =
        searchApiService.search(term, offset, limit)
}