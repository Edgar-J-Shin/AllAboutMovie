package com.dcs.presentation.ui.searchresult

import com.dcs.domain.model.MovieId

sealed interface SearchResultEffect {
    data object NavigateBack : SearchResultEffect

    data class NavigateToMovieDetails(
        val movieId: MovieId,
    ) : SearchResultEffect
}
