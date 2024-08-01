package com.dcs.data.local.datastore

import com.dcs.data.local.room.dao.UserEntityDao
import com.dcs.data.local.room.entity.mapper.toEntity
import com.dcs.data.local.room.entity.mapper.toLocalData
import com.dcs.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor(
    private val userEntityDao: UserEntityDao,
) : AuthLocalDataSource {
    override fun getUser(): Flow<User?> {
        return userEntityDao.getFirstUser()
            .map { it?.toEntity() }
    }

    override suspend fun insertUser(user: User): Result<Unit> {
        return runCatching {
            userEntityDao.insert(user.toLocalData())
        }
    }

    override suspend fun deleteUser(user: User): Result<Unit> {
        return runCatching {
            userEntityDao.delete(user.toLocalData().tmdbId)
        }
    }
}
