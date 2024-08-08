package com.dcs.domain.repository

import com.dcs.domain.model.KeywordEntity
import kotlinx.coroutines.flow.Flow

interface KeywordsRepository {

    fun getKeywords(): Flow<List<KeywordEntity>>

    fun deleteKeyword(keyword: String): Flow<Unit>

    fun deleteAll(): Flow<Unit>
}
