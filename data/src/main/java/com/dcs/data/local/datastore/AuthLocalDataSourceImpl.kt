package com.dcs.data.local.datastore

import com.dcs.data.local.room.dao.UserEntityDao
import com.dcs.data.local.room.entity.mapper.toEntity
import com.dcs.data.local.room.entity.mapper.toModel
import com.dcs.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor(
    private val userEntityDao: UserEntityDao,
) : AuthLocalDataSource {
    override fun getUser(): Flow<User?> {
        return userEntityDao.getFirstUser()
            .map { it?.toModel() }
    }

    override suspend fun insertUser(user: User): Result<Unit> {
        return runCatching {
            userEntityDao.insert(user.toEntity())
        }
    }

    override suspend fun deleteUser(user: User): Result<Unit> {
        return runCatching {
            userEntityDao.delete(user.toEntity())
        }
    }
}
