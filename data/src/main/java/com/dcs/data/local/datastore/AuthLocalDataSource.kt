package com.dcs.data.local.datastore

import com.dcs.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthLocalDataSource {

    fun getUser(): Flow<User?>

    suspend fun insertUser(user: User): Result<Unit>

    suspend fun deleteUserByTmdbId(userTmdbId: Long): Result<Unit>
}
