package com.dcs.data.repository

import androidx.annotation.WorkerThread
import com.dcs.data.di.IoDispatcher
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
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
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
            .flowOn(ioDispatcher)
    }

    override fun getUser(): Flow<User?> {
        TODO("Not yet implemented")
    }

}
