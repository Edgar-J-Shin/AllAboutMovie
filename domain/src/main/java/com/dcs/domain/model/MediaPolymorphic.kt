package com.dcs.domain.model

sealed interface MediaPolymorphic {
    data class Movie(
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
    ) : MediaPolymorphic

    data class TvShow(
        val backdropPath: String,
        val id: Int,
        val name: String,
        val originalName: String,
        val overview: String,
        val posterPath: String,
        val mediaType: String,
        val adult: Boolean,
        val originalLanguage: String,
        val genreIds: List<Int>,
        val popularity: Double,
        val firstAirDate: String,
        val voteAverage: Double,
        val voteCount: Int,
        val originalCountry: List<String>,
    ) : MediaPolymorphic
}
