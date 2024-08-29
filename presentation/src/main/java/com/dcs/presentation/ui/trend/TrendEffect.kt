package com.dcs.presentation.ui.trend

import com.dcs.domain.model.MovieId
import com.dcs.presentation.core.designsystem.state.SnackbarState

sealed interface TrendEffect {

    data class ShowSnackbar(
        val state: SnackbarState,
    ) : TrendEffect

    data class NavigateToMovieDetails(
        val movieId: MovieId,
    ) : TrendEffect
}
