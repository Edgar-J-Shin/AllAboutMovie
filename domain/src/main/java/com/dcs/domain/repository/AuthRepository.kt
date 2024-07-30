package com.dcs.domain.repository

import com.dcs.domain.model.RequestToken
import com.dcs.domain.model.SessionId
import com.dcs.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun createRequestToken(): Flow<RequestToken>

    fun createSessionId(requestToken: RequestToken): Flow<SessionId>

    fun signIn(sessionId: SessionId): Flow<User>

    fun getUser(): Flow<User?>
}
