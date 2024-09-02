package com.dcs.presentation.ui.trend

import com.dcs.domain.model.MediaContentId

sealed interface TrendUiEvent {

    data class NavigateToMovieDetails(
        val mediaContentId: MediaContentId,
    ) : TrendUiEvent
}
