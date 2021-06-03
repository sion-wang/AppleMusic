package com.sion.itunes.model.api.search

import android.util.Log
import com.sion.itunes.BuildConfig
import com.sion.itunes.model.vo.SearchResponse
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.cio.*
import io.ktor.util.*
//Ref : io.ktor.http.cio.HttpMessage
//class SearchApiRepository(private val searchApiService: SearchApiService) : ISearchApiRepository {
class SearchApiRepository(private val client: HttpClient): ISearchApiRepository {
    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

//    override suspend fun search(term: String, offset: Int, limit: Int) =
//        searchApiService.search(term, offset, limit)

    //REf : https://stackoverflow.com/posts/61742751/revisions
    @KtorExperimentalAPI
    override suspend fun search(term: String, offset: Int, limit: Int) : HttpResponse {
        try {
            val requestUrl = BuildConfig.ITUNES_API_HOST + "/search"
//            val response =
//                client.request<SearchResponse>(requestUrl) {
//                    method = HttpMethod.Get
//                    parameter("term", term)
//                    parameter("offset", offset)
//                    parameter("limit", limit)
////                    headers {
////                        append("My-Custom-Header", "HeaderValue")
////                    }
//                }
//            val response: SearchResponse = client.get(requestUrl)
//            Log.e("SearchApiRepository", "Show response : $response")
            val response: HttpResponse = client.get(requestUrl) {
                    parameter("term", term)
                    parameter("offset", offset)
                    parameter("limit", limit)
            }

           return response
//            liveData.postValue(Result.Success(response))
        } catch (e: java.lang.Exception) {
//            "Ktor_request Error: ${e.message?:"Un-traceable"}".print()
//            "Ktor_request Error: $e".print()
//            if (e.message.isValid()) {
//                liveData.postValue(Result.Error.RecoverableError(Exception(e.message)))
//            }else{
//                liveData.postValue(Result.Error.NonRecoverableError(Exception("Un-traceable")))
//            }

            return client.get("http://localhost:8080/")//FIXME
        }
    }
}