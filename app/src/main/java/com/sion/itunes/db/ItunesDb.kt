package com.sion.itunes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sion.itunes.App.Companion.DB_NAME
import com.sion.itunes.db.vo.RemoteKey
import com.sion.itunes.model.vo.Music

/**
 * Database schema used by the
 */
@Database(
    entities = [Music::class, RemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class ItunesDb : RoomDatabase() {
    companion object {
        fun create(context: Context, useInMemory: Boolean = true): ItunesDb {
            val databaseBuilder = if (useInMemory) {
                Room.inMemoryDatabaseBuilder(context, ItunesDb::class.java)
            } else {
                Room.databaseBuilder(context, ItunesDb::class.java, DB_NAME)
            }
            return databaseBuilder
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    abstract fun musics(): MusicDao
    abstract fun remoteKeys(): RemoteKeyDao
}