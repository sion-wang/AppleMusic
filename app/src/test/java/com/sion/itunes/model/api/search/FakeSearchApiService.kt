package com.sion.itunes.model.api.search

import com.sion.itunes.model.vo.SearchResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class FakeSearchApiService(private val searchResponse: SearchResponse?) : SearchApiService {
    override suspend fun search(term: String, offset: Int, limit: Int): Response<SearchResponse> {
        searchResponse?.let {
            val end = if (offset + limit > it.results.size) it.results.size else (offset + limit)
            it.results.filter { music ->
                term.isEmpty() || music.artistName.contains(term) || music.trackName.contains(term)
            }.subList(offset, end).run {
                return Response.success(SearchResponse(this.size, this))
            }
        }
        return Response.error(500, "{\"msg\":\"null response\"}"
            .toResponseBody("application/json".toMediaTypeOrNull())
        )
    }
}