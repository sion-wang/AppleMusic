package com.sion.itunes.model.api.search.db

import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.sion.itunes.db.ItunesDb
import com.sion.itunes.db.MusicDao
import com.sion.itunes.db.RemoteKeyDao

class FakeItunesDb(private val musicDao: MusicDao, private val remoteKeyDao: RemoteKeyDao): ItunesDb() {
    override fun musics(): MusicDao {
        return musicDao
    }

    override fun remoteKeys(): RemoteKeyDao {
        return remoteKeyDao
    }

    override fun createOpenHelper(config: DatabaseConfiguration?): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        return InvalidationTracker(this, "musics","remote_keys")
    }

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }
}