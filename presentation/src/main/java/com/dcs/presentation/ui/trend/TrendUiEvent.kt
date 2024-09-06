package com.dcs.presentation.ui.trend

import com.dcs.domain.model.MediaContentId
import com.dcs.domain.model.MediaType

sealed interface TrendUiEvent {

    data class OnMediaItemClick(
        val mediaContentId: MediaContentId,
        val mediaType: MediaType = MediaType.MOVIE,
    ) : TrendUiEvent
}
