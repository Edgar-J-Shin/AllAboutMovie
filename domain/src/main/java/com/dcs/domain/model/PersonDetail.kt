package com.dcs.domain.model

data class PersonDetail(
    val id: PersonId,
    val adult: Boolean,
    val alsoKnownAs: List<String>,
    val biography: String,
    val birthday: String,
    val deathday: String,
    val gender: Gender,
    val homepage: String,
    val imdbId: String,
    val knownForDepartment: String,
    val name: String,
    val placeOfBirth: String,
    val popularity: Double,
    val profilePath: String,
    val knownFor: List<KnownFor>,
    val credits: Map<String, List<PersonCredit>>,
    val profileImages: List<ProfileImage>,
    val creditCounts: Int,
)

data class PersonCredit(
    val id: MediaContentId,
    val adult: Boolean,
    val mediaType: MediaType,
    val posterPath: String,
    val releaseDate: String, // Movie release date, TV show first air date
    val title: String, // Movie title, TV show name
    val role: String, // Character name for cast, job for crew
)

data class ProfileImage(
    val aspectRatio: Double,
    val filePath: String,
    val height: Int,
    val iso6391: String,
    val voteAverage: Double,
    val voteCount: Int,
    val width: Int,
)
