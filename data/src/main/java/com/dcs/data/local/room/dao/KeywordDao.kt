package com.dcs.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dcs.data.local.room.entity.Keyword
import kotlinx.coroutines.flow.Flow

@Dao
interface KeywordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keyword: Keyword): Long

    @Query("SELECT * FROM keywords ")
    fun getKeywordAll(): Flow<Keyword>

    @Query("DELETE FROM keywords WHERE id = :keyword")
    suspend fun delete(keyword: String)
}
