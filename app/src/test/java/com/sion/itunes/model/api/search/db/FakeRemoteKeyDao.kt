package com.sion.itunes.model.api.search.db

import com.sion.itunes.db.RemoteKeyDao
import com.sion.itunes.db.vo.RemoteKey
import com.sion.itunes.model.vo.Music

class FakeRemoteKeyDao(private val remoteKeys: ArrayList<RemoteKey> = arrayListOf()): RemoteKeyDao {
    override suspend fun insert(remoteKey: RemoteKey) {
        remoteKeys.add(remoteKey)
    }

    override suspend fun remoteKeyByKeyword(keyword: String): RemoteKey {
        var result = RemoteKey("", "")
        remoteKeys.forEach {
            if (it.keyword == keyword) result = it
        }
        return result
    }

    override suspend fun deleteByKeyword(keyword: String) {
        TODO("Not yet implemented")
    }
}