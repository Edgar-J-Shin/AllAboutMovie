package com.dcs.data.local.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg entity: T): Long

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(vararg entity: T)

    @Delete
    fun delete(vararg entity: T)
}
