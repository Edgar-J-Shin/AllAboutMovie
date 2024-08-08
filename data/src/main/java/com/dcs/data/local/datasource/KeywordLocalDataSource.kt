package com.dcs.data.local.datasource

import com.dcs.domain.model.Keyword
import kotlinx.coroutines.flow.Flow

interface KeywordLocalDataSource {

    fun getKeywordAll(): Flow<List<Keyword>>

    suspend fun insertKeyword(keyword: Keyword): Result<Unit>

    suspend fun deleteKeyword(keyword: String): Result<Unit>

    suspend fun deleteAll(): Result<Unit>
}
