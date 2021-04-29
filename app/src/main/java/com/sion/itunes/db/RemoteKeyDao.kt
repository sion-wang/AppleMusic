package com.sion.itunes.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sion.itunes.db.vo.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(remoteKey: RemoteKey)

    @Query("SELECT * FROM remote_keys WHERE keyword = :keyword")
    suspend fun remoteKeyByKeyword(keyword: String): RemoteKey

    @Query("DELETE FROM remote_keys WHERE keyword = :keyword")
    suspend fun deleteByKeyword(keyword: String)
}