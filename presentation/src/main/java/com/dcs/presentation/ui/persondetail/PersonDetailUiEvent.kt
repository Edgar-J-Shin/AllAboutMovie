package com.dcs.presentation.ui.persondetail

import com.dcs.domain.model.MediaContentId
import com.dcs.presentation.core.model.MediaTypeUiState

interface PersonDetailUiEvent {

    data object OnNavigationBackButtonClick : PersonDetailUiEvent

    data class OnKnownForCardClick(
        val id: MediaContentId,
        val mediaType: MediaTypeUiState,
    ) : PersonDetailUiEvent

    data class OnActingCardClick(
        val id: MediaContentId,
        val mediaType: MediaTypeUiState,
    ) : PersonDetailUiEvent

    data class OnProductionCardClick(
        val id: MediaContentId,
        val mediaType: MediaTypeUiState,
    ) : PersonDetailUiEvent
}
