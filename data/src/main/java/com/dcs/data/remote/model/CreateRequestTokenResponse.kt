package com.dcs.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateRequestTokenResponse(
    @SerialName("request_token")
    val requestToken: String,
    @SerialName("expires_at")
    val expiresAt: String,
)
