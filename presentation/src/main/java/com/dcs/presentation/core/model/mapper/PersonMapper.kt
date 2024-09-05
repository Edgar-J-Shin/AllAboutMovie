package com.dcs.presentation.core.model.mapper

import com.dcs.domain.model.Gender
import com.dcs.domain.model.KnownFor
import com.dcs.domain.model.Person
import com.dcs.presentation.core.model.GenderUiState
import com.dcs.presentation.core.model.KnownForUiState
import com.dcs.presentation.core.model.PersonUiState

fun Person.toUiState() = PersonUiState(
    id = id,
    name = name,
    originalName = originalName,
    adult = adult,
    gender = gender.toUiState(),
    knownFor = knownFor.map { it.toUiState() },
    knownForDepartment = knownForDepartment,
    popularity = popularity,
    profilePath = profilePath,
)

fun KnownFor.toUiState() = KnownForUiState(
    id = id,
    originalName = originalName,
    name = name,
    adult = adult,
    backdropPath = backdropPath,
    firstAirDate = firstAirDate,
    genreIds = genreIds,
    mediaType = mediaType.toUiState(),
    originCountry = originCountry,
    originalLanguage = originalLanguage,
    originalTitle = originalTitle,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    video = video,
    voteAverage = voteAverage,
    voteCount = voteCount,
)

fun Gender.toUiState() = when (this) {
    Gender.NOT_SPECIFIED -> GenderUiState.NOT_SPECIFIED
    Gender.FEMALE -> GenderUiState.FEMALE
    Gender.MALE -> GenderUiState.MALE
    Gender.NON_BINARY -> GenderUiState.NON_BINARY
}
