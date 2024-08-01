package com.dcs.data.remote.service

import com.dcs.data.remote.model.CreateRequestTokenResponse
import com.dcs.data.remote.model.CreateSessionIdRequest
import com.dcs.data.remote.model.CreateSessionIdResponse
import com.dcs.data.remote.model.GetUserResponse
import com.dcs.data.remote.network.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApiService {

    /**
     * Get a new request token.
     *
     * @return [CreateRequestTokenResponse]
     */
    @GET("authentication/token/new")
    suspend fun createRequestToken(): NetworkResponse<CreateRequestTokenResponse>

    @POST("authentication/session/new")
    suspend fun createSessionId(
        @Body createSessionIdRequest: CreateSessionIdRequest,
    ): NetworkResponse<CreateSessionIdResponse>

    @GET("account")
    suspend fun getAccountDetails(
        @Query("session_id") sessionId: String,
    ): NetworkResponse<GetUserResponse>
}
