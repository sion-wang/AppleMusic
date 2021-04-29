package com.sion.itunes.db.vo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKey(
    @PrimaryKey
    val keyword: String,
    val nextPageKey: String?
)
