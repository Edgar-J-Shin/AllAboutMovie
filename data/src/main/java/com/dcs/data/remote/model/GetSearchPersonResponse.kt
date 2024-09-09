package com.dcs.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetSearchPersonResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<RemotePerson>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int,
)
