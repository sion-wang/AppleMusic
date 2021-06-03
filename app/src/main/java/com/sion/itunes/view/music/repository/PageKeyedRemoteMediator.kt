package com.sion.itunes.view.music.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bumptech.glide.load.HttpException
//import androidx.room.withTransaction
import com.sion.itunes.db.ItunesDb
import com.sion.itunes.db.MusicDao
import com.sion.itunes.db.RemoteKeyDao
import com.sion.itunes.db.vo.RemoteKey
import com.sion.itunes.model.api.search.ISearchApiRepository
import com.sion.itunes.model.api.search.SearchApiRepository
import com.sion.itunes.model.vo.Music
import com.sion.itunes.model.vo.SearchResponse
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


//import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class PageKeyedRemoteMediator(private val db: ItunesDb, private val searchApi: ISearchApiRepository, private val keyword: String ) : RemoteMediator<Int, Music>() {
    private val musicDao: MusicDao = db.musics()
    private val remoteKeyDao: RemoteKeyDao = db.remoteKeys()
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Music>): MediatorResult {
        try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = db.withTransaction {
                        remoteKeyDao.remoteKeyByKeyword(keyword)
                    }
                    if (remoteKey.nextPageKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    remoteKey.nextPageKey
                }
            }

            val result = searchApi.search(keyword, loadKey?.toInt() ?: 0)
            val httpstatus_code = result.status
            val body = result.readText()
            val currentTime = System.currentTimeMillis()
            var items: List<Music> =arrayListOf()
            if (httpstatus_code.isSuccess()){
                val searchresponse = Json {
                    ignoreUnknownKeys = true
                }.decodeFromString<SearchResponse>(body)
                items=searchresponse.results
            }
            items.mapIndexed { index, music ->
                music.keyword = keyword
                music.insertIndex = currentTime + index
                music
            }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    musicDao.deleteByKeyword(keyword)
                    remoteKeyDao.deleteByKeyword(keyword)
                }

                val nextPageKey = (loadKey?.toInt()?:0) + SearchApiRepository.NETWORK_PAGE_SIZE
                remoteKeyDao.insert(RemoteKey(keyword, nextPageKey.toString()))
                musicDao.insertAll(items)
            }
            return MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}