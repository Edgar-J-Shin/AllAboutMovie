package com.dcs.data.model.mapper

import com.dcs.data.remote.model.RemoteKnownFor
import com.dcs.data.remote.model.RemotePerson
import com.dcs.domain.model.KnownFor
import com.dcs.domain.model.Person

fun RemotePerson.toEntity() = Person(
    id = id,
    name = name,
    originalName = originalName,
    adult = adult,
    gender = gender,
    knownFor = knownFor.map { it.toEntity() },
    knownForDepartment = knownForDepartment,
    popularity = popularity,
    profilePath = profilePath,
)

fun RemoteKnownFor.toEntity() = KnownFor(
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
