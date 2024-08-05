package com.dcs.presentation.core.model

data class PersonUiState(
    val id: Int,
    val name: String,
    val originalName: String,
    val adult: Boolean,
    val gender: Int,
    val knownFor: List<KnownForUiState>,
    val knownForDepartment: String,
    val popularity: Double,
    val profilePath: String,
)

data class KnownForUiState(
    val id: Int,
    val originalName: String,
    val name: String,
    val adult: Boolean,
    val backdropPath: String,
    val firstAirDate: String,
    val genreIds: List<Int>,
    val mediaType: String,
    val originCountry: List<String>,
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
)
