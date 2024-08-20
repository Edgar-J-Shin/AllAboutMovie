package com.dcs.presentation.ui.searchresult

import com.dcs.domain.model.MovieId

sealed interface SearchResultUiEvent {

    data object NavigateBack : SearchResultUiEvent

    data class NavigateToMovieDetails(
        val movieId: MovieId,
    ) : SearchResultUiEvent
}
