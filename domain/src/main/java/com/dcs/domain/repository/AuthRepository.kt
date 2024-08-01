package com.dcs.domain.repository

import com.dcs.domain.model.RequestToken
import com.dcs.domain.model.SessionId
import com.dcs.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun createRequestToken(): Flow<RequestToken>

    fun createSessionId(requestToken: RequestToken): Flow<SessionId>

    fun signIn(sessionId: SessionId): Flow<User>

    fun signOut(userTmdbId: Long, sessionId: SessionId): Flow<Unit>

    fun getUser(): Flow<User?>

    suspend fun insertUser(user: User): Result<Unit>

    suspend fun deleteUserByTmdbId(userTmdbId: Long): Result<Unit>
}
