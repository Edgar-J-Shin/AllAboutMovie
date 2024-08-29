package com.dcs.presentation.ui.trend

import com.dcs.domain.model.MovieId

sealed interface TrendUiEvent {

    data class NavigateToMovieDetails(
        val movieId: MovieId,
    ) : TrendUiEvent
}
