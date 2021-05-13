package com.sion.itunes.view.music.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sion.itunes.model.api.search.SearchApiRepository
import com.sion.itunes.model.api.search.SearchApiRepository.Companion.NETWORK_PAGE_SIZE
import com.sion.itunes.model.vo.Music
import retrofit2.HttpException
import timber.log.Timber
import java.lang.Exception

class MusicPagingSource(private val searchApiRepository: SearchApiRepository, private val keyword: String) :
    PagingSource<Int, Music>() {
    override fun getRefreshKey(state: PagingState<Int, Music>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Music> {
        return try {
            val offset = params.key ?: 0
            val result = searchApiRepository.search(keyword, offset)
            if (result.isSuccessful) {
                val musics = result.body()?.results ?: arrayListOf()
                val nextKey =
                    if (result.body()?.resultCount ?: 0 < NETWORK_PAGE_SIZE) null else offset + NETWORK_PAGE_SIZE
                LoadResult.Page(
                    data = musics,
                    prevKey = null,
                    nextKey = nextKey
                )
            } else {
                throw HttpException(result)
            }
        } catch (e: Exception) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }
}