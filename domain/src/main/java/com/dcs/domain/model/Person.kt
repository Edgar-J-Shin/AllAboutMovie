package com.dcs.domain.model


data class Person(
    val id: PersonId,
    val name: String,
    val originalName: String,
    val adult: Boolean,
    val gender: Gender,
    val knownFor: List<KnownFor>,
    val knownForDepartment: String,
    val popularity: Double,
    val profilePath: String,
)

data class KnownFor(
    val id: MediaContentId,
    val name: String,
    val originalName: String,
    val adult: Boolean,
    val backdropPath: String,
    val firstAirDate: String,
    val genreIds: List<Int>,
    val mediaType: MediaType,
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

enum class Gender {
    NOT_SPECIFIED,
    FEMALE,
    MALE,
    NON_BINARY
}
