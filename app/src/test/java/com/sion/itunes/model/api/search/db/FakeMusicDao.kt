package com.sion.itunes.model.api.search.db

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sion.itunes.db.MusicDao
import com.sion.itunes.model.api.search.SearchApiRepository
import com.sion.itunes.model.vo.Music
import timber.log.Timber
import java.lang.Exception

class FakeMusicDao(private val musics: ArrayList<Music> = arrayListOf()) : MusicDao {
    override suspend fun insertAll(musics: List<Music>) {
        this.musics.addAll(musics)
    }
    override fun musicsByKeyword(keyword: String): PagingSource<Int, Music> {
        return object : PagingSource<Int, Music>() {
            override fun getRefreshKey(state: PagingState<Int, Music>): Int? {
                return state.anchorPosition
            }
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Music> {
                return try {
                    val offset = params.key ?: 0
                    val nextKey =
                        if (offset + SearchApiRepository.NETWORK_PAGE_SIZE > musics.size) null else offset + SearchApiRepository.NETWORK_PAGE_SIZE
                    val data = musics.subList(offset, nextKey ?: musics.size)

                    LoadResult.Page(
                        data = data,
                        prevKey = null,
                        nextKey = nextKey
                    )
                } catch (e: Exception) {
                    Timber.e(e)
                    LoadResult.Error(e)
                }
            }
        }
    }

    override suspend fun deleteByKeyword(keyword: String) {
        TODO("Not yet implemented")
    }
}