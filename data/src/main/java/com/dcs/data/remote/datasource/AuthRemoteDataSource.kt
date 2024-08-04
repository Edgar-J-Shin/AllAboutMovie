package com.dcs.data.remote.datasource

import com.dcs.data.remote.model.CreateRequestTokenResponse
import com.dcs.data.remote.model.CreateSessionIdResponse
import com.dcs.data.remote.model.GetUserResponse
import com.dcs.domain.model.SessionId

interface AuthRemoteDataSource {

    suspend fun createRequestToken(): Result<CreateRequestTokenResponse>

    suspend fun createSessionId(requestToken: String): Result<CreateSessionIdResponse>

    suspend fun getUser(sessionId: SessionId): Result<GetUserResponse>

    suspend fun deleteSession(sessionId: SessionId): Result<Unit>
}
