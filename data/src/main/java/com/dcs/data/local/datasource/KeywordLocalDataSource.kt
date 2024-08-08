package com.dcs.data.local.datasource

import com.dcs.domain.model.KeywordEntity
import kotlinx.coroutines.flow.Flow

interface KeywordLocalDataSource {

    fun getKeywordAll(): Flow<List<KeywordEntity>>

    suspend fun insertKeyword(keywordEntity: KeywordEntity): Result<Unit>

    suspend fun deleteKeyword(keyword: String): Result<Unit>

    suspend fun deleteAll(): Result<Unit>
}
