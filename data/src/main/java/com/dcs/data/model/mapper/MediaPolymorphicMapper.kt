package com.dcs.data.model.mapper

import com.dcs.data.remote.model.RemoteMediaPolymorphic
import com.dcs.domain.model.MediaPolymorphic

fun RemoteMediaPolymorphic.RemoteMovie.toEntity() = MediaPolymorphic.Movie(
    adult = adult,
    backdropPath = backdropPath,
    genreIds = genreIds,
    id = id,
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


fun RemoteMediaPolymorphic.RemoteTvShow.toEntity() = MediaPolymorphic.TvShow(
    adult = adult,
    backdropPath = backdropPath,
    genreIds = genreIds,
    id = id,
    mediaType = mediaType,
    originalLanguage = originalLanguage,
    overview = overview,
    popularity = popularity,
    posterPath = posterPath,
    voteAverage = voteAverage,
    voteCount = voteCount,
    name = name,
    originalName = originalName,
    firstAirDate = firstAirDate,
    originalCountry = originalCountry
)

fun RemoteMediaPolymorphic.toEntity() = when (this) {
    is RemoteMediaPolymorphic.RemoteMovie -> toEntity()
    is RemoteMediaPolymorphic.RemoteTvShow -> toEntity()
}
