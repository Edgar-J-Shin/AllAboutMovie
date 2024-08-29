package com.dcs.presentation.core.model.mapper

import com.dcs.domain.model.MediaType
import com.dcs.domain.model.Movie
import com.dcs.domain.model.TimeWindow
import com.dcs.presentation.core.model.MovieUiState
import com.dcs.presentation.core.state.MoviePopularUiType
import com.dcs.presentation.core.state.MovieTrendUiType

fun Movie.toUiState() = MovieUiState(
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

fun MovieTrendUiType.toTimeWindow() = TimeWindow.timeWindow(this.toString())

fun MoviePopularUiType.toMediaType() = MediaType.mediaType(this.toString())
