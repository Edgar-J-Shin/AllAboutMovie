package com.dcs.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "tmdb_id")
    val tmdbId: Long,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "user_name")
    val username: String,

    @ColumnInfo(name = "session_id")
    val sessionId: String,

    @ColumnInfo(name = "include_adult")
    val includeAdult: Boolean,

    @ColumnInfo(name = "iso_3166_1")
    val iso31661: String,

    @ColumnInfo(name = "iso_639_1")
    val iso6391: String,

    @Embedded
    val avatar: Avatar,
)

data class Avatar(
    @ColumnInfo(name = "gravatar_hash")
    val gravatarHash: String,

    @ColumnInfo(name = "tmdb_url")
    val tmdbUrl: String?,
)
