package com.dcs.data.repository

import androidx.annotation.WorkerThread
import com.dcs.data.di.IoDispatcher
import com.dcs.data.remote.datasource.AuthRemoteDataSource
import com.dcs.domain.model.Avatar
import com.dcs.domain.model.GravatarHash
import com.dcs.domain.model.RequestToken
import com.dcs.domain.model.SessionId
import com.dcs.domain.model.TmdbAvatarPath
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

            emit(RequestToken(response.requestToken))
        }
            .flowOn(ioDispatcher)
    }

    @WorkerThread
    override fun createSessionId(requestToken: RequestToken): Flow<SessionId> {
        return flow {
            val response = authRemoteDataSource.createSessionId(
                requestToken = requestToken.value
            ).getOrThrow()

            emit(SessionId(response.sessionId))
        }
            .flowOn(ioDispatcher)
    }

    override fun signIn(sessionId: SessionId, accountId: String?): Flow<User> {
        return flow {
            val response = authRemoteDataSource.getUser(
                sessionId = sessionId,
            ).getOrThrow()

            // TODO: Mapper 생성
            val user = User(
                id = response.id,
                name = response.name,
                username = response.username,
                avatar = Avatar(
                    gravatar = GravatarHash(response.avatar.gravatar.hash),
                    tmdb = TmdbAvatarPath(response.avatar.tmdb.avatarPath)

                ),
                includeAdult = response.includeAdult,
                iso31661 = response.iso31661,
                iso6391 = response.iso6391,
                sessionId = sessionId
            )
            emit(user)
        }
            .flowOn(ioDispatcher)
    }

    override fun getUser(): Flow<User?> {
        TODO("Not yet implemented")
    }

}
