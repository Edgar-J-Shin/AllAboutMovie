package com.dcs.presentation.core.model.mapper

import com.dcs.domain.model.Cast
import com.dcs.domain.model.Crew
import com.dcs.domain.model.PersonDetail
import com.dcs.presentation.ui.persondetail.CastUiState
import com.dcs.presentation.ui.persondetail.CrewUiState
import com.dcs.presentation.ui.persondetail.PersonDetailUiState

fun PersonDetail.toUiState() = PersonDetailUiState(
    id = id,
    adult = adult,
    alsoKnownAs = alsoKnownAs,
    biography = biography,
    birthday = birthday,
    deathday = deathday,
    gender = gender,
    homepage = homepage,
    imdbId = imdbId,
    knownForDepartment = knownForDepartment,
    name = name,
    placeOfBirth = placeOfBirth,
    popularity = popularity,
    profilePath = profilePath,
    casts = casts.map { it.toUiState() },
    crews = crews.map { it.toUiState() },
    knownFor = knownFor.map { it.toUiState() },
)

fun Crew.toUiState() = CrewUiState(
    id = id,
    adult = adult,
    backdropPath = backdropPath,
    creditId = creditId,
    department = department,
    genreIds = genreIds,
    job = job,
    mediaType = mediaType,
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

fun Cast.toUiState() = CastUiState(
    id = id,
    adult = adult,
    backdropPath = backdropPath,
    character = character,
    creditId = creditId,
    episodeCount = episodeCount,
    firstAirDate = firstAirDate,
    genreIds = genreIds,
    mediaType = mediaType,
    name = name,
    order = order,
    originCountry = originCountry,
    originalLanguage = originalLanguage,
    originalName = originalName,
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
