package com.dcs.presentation.ui.searchresult

import com.dcs.domain.model.MediaContentId

sealed interface SearchResultUiEvent {

    data object NavigateBack : SearchResultUiEvent

    data class NavigateToMovieDetails(
        val mediaContentId: MediaContentId,
    ) : SearchResultUiEvent
}
