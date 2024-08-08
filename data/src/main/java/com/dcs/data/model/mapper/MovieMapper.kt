package com.dcs.data.model.mapper

import com.dcs.data.remote.model.RemoteMovie
import com.dcs.domain.model.Movie

fun RemoteMovie.toEntity() = Movie(
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
    name = name,
    originalName = originalName,
    firstAirDate = firstAirDate,
    originalCountry = originalCountry
)
