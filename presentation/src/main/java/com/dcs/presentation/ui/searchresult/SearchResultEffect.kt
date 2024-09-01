package com.dcs.presentation.ui.searchresult

import com.dcs.domain.model.MediaContentId

sealed interface SearchResultEffect {
    data object NavigateBack : SearchResultEffect

    data class NavigateToMovieDetails(
        val mediaContentId: MediaContentId,
    ) : SearchResultEffect
}
