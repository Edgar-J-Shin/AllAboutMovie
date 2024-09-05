package com.dcs.presentation.ui.trend

import com.dcs.domain.model.MediaContentId
import com.dcs.presentation.core.designsystem.state.SnackbarState

sealed interface TrendEffect {

    data class ShowSnackbar(
        val state: SnackbarState,
    ) : TrendEffect

    data class NavigateToMovieDetails(
        val mediaContentId: MediaContentId,
    ) : TrendEffect

    data class NavigateToTvShowDetails(
        val mediaContentId: MediaContentId,
    ) : TrendEffect
}
