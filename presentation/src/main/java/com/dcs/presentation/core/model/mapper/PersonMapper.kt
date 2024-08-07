package com.dcs.presentation.core.model.mapper

import com.dcs.domain.model.KnownFor
import com.dcs.domain.model.Person
import com.dcs.presentation.core.model.KnownForUiState
import com.dcs.presentation.core.model.PersonUiState

fun Person.toUiState() = PersonUiState(
    id = id,
    name = name,
    originalName = originalName,
    adult = adult,
    gender = gender,
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
    mediaType = mediaType,
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
