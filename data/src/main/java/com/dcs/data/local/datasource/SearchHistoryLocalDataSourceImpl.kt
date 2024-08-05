package com.dcs.data.local.datasource

import com.dcs.data.local.room.dao.KeywordDao
import com.dcs.data.local.room.entity.mapper.toEntity
import com.dcs.data.local.room.entity.mapper.toLocalData
import com.dcs.domain.model.Keyword
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchHistoryLocalDataSourceImpl @Inject constructor(
    private val keywordDao: KeywordDao,
) : SearchHistoryLocalDataSource {

    override fun getSearchHistory(): Flow<Keyword> {
        return keywordDao.getKeywordAll()
            .map { it.toEntity() }
            .distinctUntilChanged()
    }

    override suspend fun insertSearchKeyword(keyword: Keyword): Result<Unit> {
        return runCatching {
            keywordDao.insert(keyword.toLocalData())
        }
    }

    override suspend fun deleteSearchKeyword(keyword: String): Result<Unit> {
        return runCatching {
            keywordDao.delete(keyword)
        }
    }
}
