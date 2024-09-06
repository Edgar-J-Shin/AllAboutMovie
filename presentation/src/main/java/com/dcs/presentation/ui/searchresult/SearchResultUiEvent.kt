package com.dcs.presentation.ui.searchresult

import com.dcs.domain.model.MediaContentId

sealed interface SearchResultUiEvent {

    data object OnNavigationBackClick : SearchResultUiEvent

    data class OnMediaItemClick(
        val mediaContentId: MediaContentId,
    ) : SearchResultUiEvent
}
