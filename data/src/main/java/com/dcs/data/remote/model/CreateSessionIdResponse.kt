package com.dcs.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateSessionIdResponse(
    @SerialName("session_id")
    val sessionId: String,
)

@Serializable
data class CreateSessionIdRequest(
    @SerialName("request_token")
    val requestToken: String,
)
