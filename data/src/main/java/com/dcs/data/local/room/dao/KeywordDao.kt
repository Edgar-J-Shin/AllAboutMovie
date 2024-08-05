package com.dcs.data.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.dcs.data.local.room.entity.Keyword
import kotlinx.coroutines.flow.Flow

@Dao
interface KeywordDao: BaseDao<Keyword> {

    @Query("SELECT * FROM keywords ")
    fun getKeywordAll(): Flow<Keyword>

    @Query("DELETE FROM keywords WHERE id = :keyword")
    suspend fun delete(keyword: String)
}
