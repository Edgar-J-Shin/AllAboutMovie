package com.dcs.presentation.core.model.mapper

import com.dcs.domain.model.MediaPolymorphic
import com.dcs.domain.model.TimeWindow
import com.dcs.presentation.core.model.MediaContentUiState
import com.dcs.presentation.core.model.TrendingMovieUiType

fun MediaPolymorphic.Movie.toUiState() = MediaContentUiState(
    id = id,
    mediaType = mediaType,
    title = title.ifEmpty { originalTitle },
    posterPath = posterPath,
    voteAverage = voteAverage,
    releaseDate = releaseDate
)


fun MediaPolymorphic.TvShow.toUiState() = MediaContentUiState(
    id = id,
    mediaType = mediaType,
    title = name.ifEmpty { originalName },
    posterPath = posterPath,
    voteAverage = voteAverage,
    releaseDate = firstAirDate
)

fun MediaPolymorphic.toUiState() = when (this) {
    is MediaPolymorphic.Movie -> toUiState()
    is MediaPolymorphic.TvShow -> toUiState()
}

fun TrendingMovieUiType.toTimeWindow() = TimeWindow.timeWindow(this.toString())
