package com.dcs.domain.model

data class Person(
    val id: Int,
    val name: String,
    val originalName: String,
    val adult: Boolean,
    val gender: Int,
    val knownFor: List<KnownFor>,
    val knownForDepartment: String,
    val popularity: Double,
    val profilePath: String,
)

data class KnownFor(
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
