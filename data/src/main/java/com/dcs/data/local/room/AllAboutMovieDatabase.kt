package com.dcs.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dcs.data.local.room.dao.KeywordDao
import com.dcs.data.local.room.dao.UserDao
import com.dcs.data.local.room.entity.Keyword
import com.dcs.data.local.room.entity.User

/**
 * The [RoomDatabase] for this app.
 */
@Database(
    entities = [
        User::class, Keyword::class
    ],
    version = 2,
)
abstract class AllAboutMovieDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun keywordDao(): KeywordDao

    companion object {
        const val DATABASE_NAME = "dcs-allaboutmoive.db"
    }
}
