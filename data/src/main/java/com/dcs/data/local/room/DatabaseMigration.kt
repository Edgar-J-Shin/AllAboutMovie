package com.dcs.data.local.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE `keywords` (`keyword` TEXT NOT NULL, PRIMARY KEY(`keyword`))")
    }
}
