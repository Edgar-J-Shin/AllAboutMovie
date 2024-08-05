package com.dcs.data.repository

import androidx.annotation.WorkerThread
import com.dcs.data.di.IoDispatcher
import com.dcs.data.local.datasource.AuthLocalDataSource
import com.dcs.data.model.mapper.toEntity
import com.dcs.data.remote.datasource.AuthRemoteDataSource
import com.dcs.domain.model.RequestToken
import com.dcs.domain.model.SessionId
import com.dcs.domain.model.User
import com.dcs.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
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

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun signIn(sessionId: SessionId): Flow<User> {
        return flow {
            val response = authRemoteDataSource.getUser(
                sessionId = sessionId,
            ).getOrThrow()

            emit(response.toEntity(sessionId))
        }
            .flatMapLatest { user ->
                insertUser(user)
            }
            .flowOn(ioDispatcher)
    }

    override fun signOut(userId: Long, sessionId: SessionId): Flow<Unit> {
        return combine(
            flow {
                emit(
                    authRemoteDataSource
                        .deleteSession(
                            sessionId = sessionId,
                        )
                        .getOrDefault(Unit)
                )
            },
            deleteUserByUserId(userId),
        ) { _, _ -> }
            .flowOn(ioDispatcher)
    }

    override fun getUser(): Flow<User?> {
        return authLocalDataSource.getUser()
            .flowOn(ioDispatcher)
    }

    override fun insertUser(user: User): Flow<User> {
        return flow {
            val result = authLocalDataSource.insertUser(user)
                .getOrThrow()

            emit(result)
        }
            .flowOn(ioDispatcher)
    }

    override fun deleteUserByUserId(userId: Long): Flow<Unit> {
        return flow {
            emit(authLocalDataSource.deleteUserByUserId(userId).getOrThrow())
        }
            .flowOn(ioDispatcher)
    }
}
