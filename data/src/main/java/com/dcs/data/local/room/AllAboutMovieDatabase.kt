package com.dcs.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dcs.data.local.room.dao.UserEntityDao
import com.dcs.data.local.room.entity.User

/**
 * The [RoomDatabase] for this app.
 */
@Database(
    entities = [
        User::class,
    ],
    version = 1,
)
abstract class AllAboutMovieDatabase : RoomDatabase() {

    abstract fun userEntityDao(): UserEntityDao

    companion object {
        const val DATABASE_NAME = "dcs-allaboutmoive.db"
    }
}
