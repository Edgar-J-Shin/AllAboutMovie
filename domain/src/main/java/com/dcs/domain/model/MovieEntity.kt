package com.dcs.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieEntity(
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val mediaType: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val name: String,
    val originalName: String,
    val firstAirDate: String,
    val originalCountry: List<String>
)