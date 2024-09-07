package com.dcs.presentation.core.model.mapper

import com.dcs.domain.model.MediaType
import com.dcs.domain.model.PersonCredit
import com.dcs.domain.model.PersonDetail
import com.dcs.presentation.core.model.MediaTypeUiState
import com.dcs.presentation.core.model.PersonCreditUiState
import com.dcs.presentation.core.model.PersonDetailUiState

fun PersonDetail.toUiState() = PersonDetailUiState(
    id = id,
    adult = adult,
    alsoKnownAs = alsoKnownAs,
    biography = biography,
    birthday = birthday,
    deathday = deathday,
    gender = gender.toUiState(),
    homepage = homepage,
    imdbId = imdbId,
    knownForDepartment = knownForDepartment,
    name = name,
    placeOfBirth = placeOfBirth,
    popularity = popularity,
    profilePath = profilePath,
    knownFor = knownFor.map { it.toUiState() },
    credits = credits.mapValues {
        it.value.map { item -> item.toUiState() }
    }
)

fun MediaType.toUiState() = when (this) {
    MediaType.MOVIE -> MediaTypeUiState.MOVIE
    MediaType.TV_SHOW -> MediaTypeUiState.TV
}

fun PersonCredit.toUiState() = PersonCreditUiState(
    id = id,
    adult = adult,
    mediaType = mediaType.toUiState(),
    posterPath = posterPath,
    releaseDate = releaseDate,
    title = title,
    role = role,
)
