package com.dcs.data.repository

import androidx.annotation.WorkerThread
import com.dcs.data.di.IoDispatcher
import com.dcs.data.local.datastore.AuthLocalDataSource
import com.dcs.data.model.mapper.toEntity
import com.dcs.data.remote.datasource.AuthRemoteDataSource
import com.dcs.domain.model.RequestToken
import com.dcs.domain.model.SessionId
import com.dcs.domain.model.User
import com.dcs.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : AuthRepository {

    @WorkerThread
    override fun createRequestToken(): Flow<RequestToken> {
        return flow {
            val response = authRemoteDataSource.createRequestToken()
                .getOrThrow()

            emit(response.toEntity())
        }
            .flowOn(ioDispatcher)
    }

    @WorkerThread
    override fun createSessionId(requestToken: RequestToken): Flow<SessionId> {
        return flow {
            val response = authRemoteDataSource.createSessionId(
                requestToken = requestToken.value
            ).getOrThrow()

            emit(response.toEntity())
        }
            .flowOn(ioDispatcher)
    }

    override fun signIn(sessionId: SessionId): Flow<User> {
        return flow {
            val response = authRemoteDataSource.getUser(
                sessionId = sessionId,
            ).getOrThrow()

            emit(response.toEntity(sessionId))
        }
            .onEach { user ->
                insertUser(user)
            }
            .flowOn(ioDispatcher)
    }

    override fun getUser(): Flow<User?> {
        return authLocalDataSource.getUser()
            .flowOn(ioDispatcher)
    }

    override suspend fun insertUser(user: User): Result<Unit> {
        return authLocalDataSource.insertUser(user)
    }

    override suspend fun deleteUser(user: User): Result<Unit> {
        return authLocalDataSource.deleteUser(user)
    }
}
