package com.dcs.data.remote.datasource

import com.dcs.data.remote.model.CreateRequestTokenResponse
import com.dcs.data.remote.model.CreateSessionIdRequest
import com.dcs.data.remote.model.CreateSessionIdResponse
import com.dcs.data.remote.model.GetUserResponse
import com.dcs.data.remote.service.AuthApiService
import com.dcs.domain.model.SessionId
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val api: AuthApiService,
) : AuthRemoteDataSource {
    override suspend fun createRequestToken(): Result<CreateRequestTokenResponse> {
        return api.createRequestToken()
            .asResult {
                it.data as CreateRequestTokenResponse
            }
    }

    override suspend fun createSessionId(requestToken: String): Result<CreateSessionIdResponse> {
        return api.createSessionId(
            createSessionIdRequest = CreateSessionIdRequest(
                requestToken = requestToken
            )
        )
            .asResult {
                it.data as CreateSessionIdResponse
            }
    }

    override suspend fun deleteSession(sessionId: SessionId): Result<Unit> {
        return api.deleteSession(
            sessionId = sessionId.value
        )
            .asResult {
                Unit
            }
    }

    override suspend fun getUser(sessionId: SessionId): Result<GetUserResponse> {
        return api.getAccountDetails(
            sessionId = sessionId.value
        )
            .asResult {
                it.data as GetUserResponse
            }
    }
}
