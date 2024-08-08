package com.dcs.domain.repository

import com.dcs.domain.model.Keyword
import kotlinx.coroutines.flow.Flow

interface KeywordsRepository {

    fun getKeywords(): Flow<List<Keyword>>

    fun deleteKeyword(keyword: String): Flow<Unit>

    fun deleteAll(): Flow<Unit>
}
