package com.dcs.data.model.mapper

import com.dcs.data.remote.model.GetPersonDetailResponse
import com.dcs.data.remote.model.RemoteCast
import com.dcs.data.remote.model.RemoteCrew
import com.dcs.domain.model.KnownFor
import com.dcs.domain.model.PersonDetail

fun GetPersonDetailResponse.toEntity(knownFor: List<KnownFor>) = PersonDetail(
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
    credits = combinedCredits.cast.map { it.toEntity() },
    crews = combinedCredits.crew.map { it.toEntity() },
    knownFor = knownFor,
)

fun RemoteCast.toEntity() = com.dcs.domain.model.Cast(
    adult = adult,
    backdropPath = backdropPath,
    character = character,
    creditId = creditId,
    episodeCount = episodeCount,
    firstAirDate = firstAirDate,
    genreIds = genreIds,
    id = id,
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

fun RemoteCrew.toEntity() = com.dcs.domain.model.Crew(
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

