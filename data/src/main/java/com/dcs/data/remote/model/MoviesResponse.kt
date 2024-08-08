package com.dcs.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val results: List<RemoteMovie>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int,
)


@Serializable
data class RemoteMovie(
    @SerialName("adult")
    val adult: Boolean = false,
    @SerialName("backdrop_path")
    val backdropPath: String = "",
    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),
    @SerialName("id")
    val id: Int,
    @SerialName("media_type")
    val mediaType: String = "",
    @SerialName("original_language")
    val originalLanguage: String = "",
    @SerialName("original_title")
    val originalTitle: String = "",
    @SerialName("overview")
    val overview: String = "",
    @SerialName("popularity")
    val popularity: Double = 0.0,
    @SerialName("poster_path")
    val posterPath: String = "",
    @SerialName("release_date")
    val releaseDate: String = "",
    @SerialName("title")
    val title: String = "",
    @SerialName("video")
    val video: Boolean = false,
    @SerialName("vote_average")
    val voteAverage: Double = 0.0,
    @SerialName("vote_count")
    val voteCount: Int = 0,
    @SerialName("name")
    val name: String = "",
    @SerialName("original_name")
    val originalName: String = "",
    @SerialName("first_air_date")
    val firstAirDate: String = "",
    @SerialName("original_country")
    val originalCountry: List<String> = emptyList()
)
