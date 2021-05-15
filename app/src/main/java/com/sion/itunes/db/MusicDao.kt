package com.sion.itunes.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sion.itunes.model.vo.Music

@Dao
interface MusicDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(musics: List<Music>)

    @Query("SELECT * FROM musics WHERE keyword = :keyword ORDER BY insertIndex ASC")
    fun musicsByKeyword(keyword: String): PagingSource<Int, Music>

    @Query("DELETE FROM musics WHERE keyword = :keyword")
    suspend fun deleteByKeyword(keyword: String)

}