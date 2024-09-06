package com.dcs.data.model.mapper

import com.dcs.data.remote.model.RemoteGender
import com.dcs.data.remote.model.RemoteKnownFor
import com.dcs.data.remote.model.RemotePerson
import com.dcs.domain.model.Gender
import com.dcs.domain.model.KnownFor
import com.dcs.domain.model.MediaContentId
import com.dcs.domain.model.Person

fun RemotePerson.toEntity() = Person(
    id = id,
    name = name,
    originalName = originalName,
    adult = adult,
    gender = gender.toEntity(),
    knownFor = knownFor.map { it.toEntity() },
    knownForDepartment = knownForDepartment,
    popularity = popularity,
    profilePath = profilePath,
)

fun RemoteKnownFor.toEntity() = KnownFor(
    id = MediaContentId(id),
    originalName = originalName,
    name = name,
    adult = adult,
    backdropPath = backdropPath,
    firstAirDate = firstAirDate,
    genreIds = genreIds,
    mediaType = mediaType.toEntity(),
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

fun RemoteGender.toEntity() = when (this) {
    RemoteGender.NOT_SPECIFIED -> Gender.NOT_SPECIFIED
    RemoteGender.FEMALE -> Gender.FEMALE
    RemoteGender.MALE -> Gender.MALE
    RemoteGender.NON_BINARY -> Gender.NON_BINARY
}
