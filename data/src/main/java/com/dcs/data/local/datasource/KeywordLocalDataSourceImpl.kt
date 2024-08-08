package com.dcs.data.local.datasource

import androidx.annotation.WorkerThread
import com.dcs.data.local.room.dao.KeywordDao
import com.dcs.data.local.room.entity.mapper.toEntity
import com.dcs.data.local.room.entity.mapper.toLocalData
import com.dcs.domain.model.KeywordEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class KeywordLocalDataSourceImpl @Inject constructor(
    private val keywordDao: KeywordDao,
) : KeywordLocalDataSource {
    @WorkerThread
    override fun getKeywordAll(): Flow<List<KeywordEntity>> {
        return keywordDao.getKeywordAll()
            .map { keywords -> keywords.map { it.toEntity() } }
            .distinctUntilChanged()
    }

    @WorkerThread
    override suspend fun insertKeyword(keywordEntity: KeywordEntity): Result<Unit> {
        return runCatching {
            keywordDao.insert(keywordEntity.toLocalData())
        }
    }

    @WorkerThread
    override suspend fun deleteKeyword(keyword: String): Result<Unit> {
        return runCatching {
            keywordDao.delete(keyword)
        }
    }

    @WorkerThread
    override suspend fun deleteAll(): Result<Unit> {
        return runCatching {
            keywordDao.deleteAll()
        }
    }
}
