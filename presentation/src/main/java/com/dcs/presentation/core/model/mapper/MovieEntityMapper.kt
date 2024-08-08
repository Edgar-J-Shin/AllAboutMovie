package com.dcs.presentation.core.model.mapper

import com.dcs.domain.model.Movie
import com.dcs.presentation.core.model.MovieItemUiState

fun Movie.toUiState() = MovieItemUiState(
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
