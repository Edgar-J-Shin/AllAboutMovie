package com.dcs.data.local.datasource

import com.dcs.domain.model.Keyword
import kotlinx.coroutines.flow.Flow

interface SearchHistoryLocalDataSource {

    fun getSearchHistory(): Flow<Keyword>

    suspend fun insertSearchKeyword(keyword: Keyword): Result<Unit>

    suspend fun deleteSearchKeyword(keyword: String): Result<Unit>
}
