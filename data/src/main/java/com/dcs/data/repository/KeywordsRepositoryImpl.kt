package com.dcs.data.repository

import com.dcs.data.di.IoDispatcher
import com.dcs.data.local.datasource.KeywordLocalDataSource
import com.dcs.domain.model.Keyword
import com.dcs.domain.repository.KeywordsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class KeywordsRepositoryImpl @Inject constructor(
    private val keywordLocalDataSource: KeywordLocalDataSource,
    @IoDispatcher val ioDispatcher: CoroutineDispatcher,
) : KeywordsRepository {
    override fun getKeywords(): Flow<List<Keyword>> =
        keywordLocalDataSource.getKeywordAll()
            .flowOn(ioDispatcher)

    override fun deleteKeyword(keyword: String): Flow<Unit> = flow<Unit> {
        keywordLocalDataSource.deleteKeyword(keyword)
    }.flowOn(ioDispatcher)

    override fun deleteAll(): Flow<Unit> = flow<Unit> {
        keywordLocalDataSource.deleteAll()
    }.flowOn(ioDispatcher)
}
