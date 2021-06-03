package com.sion.itunes.view.music.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sion.itunes.model.api.search.ISearchApiRepository
//import com.sion.itunes.model.api.search.ISearchApiRepository
//import com.sion.itunes.model.api.search.SearchApiRepository
import com.sion.itunes.model.api.search.SearchApiRepository.Companion.NETWORK_PAGE_SIZE
import com.sion.itunes.model.vo.Music
import com.sion.itunes.model.vo.SearchResponse
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.JsonConfiguration
//import retrofit2.HttpException
import timber.log.Timber
import java.lang.Exception

class MusicPagingSource(
    private val searchApiRepository: ISearchApiRepository,
    private val keyword: String
) : PagingSource<Int, Music>() {

    override fun getRefreshKey(state: PagingState<Int, Music>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Music> {
        return try {
            val offset = params.key ?: 0
            val result = searchApiRepository.search(keyword, offset)
            Log.e("SSS","MusicPagingSource load  go")
            if (result.status.isSuccess()) {
                val bodystring = result.readText()
                Log.e("SSS","MusicPagingSource load : $bodystring")
                //FIXME
                val searchresponse =
                    kotlinx.serialization.json.Json.decodeFromString<SearchResponse>(bodystring)
                val musics = searchresponse.results
                val nextKey =
                    if (searchresponse.resultCount ?: 0 < NETWORK_PAGE_SIZE) null else offset + NETWORK_PAGE_SIZE
                LoadResult.Page(
                    data = musics,
                    prevKey = null,
                    nextKey = nextKey
                )
            } else {
//                throw HttpException(result)
                throw Exception()
            }
        } catch (e: Exception) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }
}