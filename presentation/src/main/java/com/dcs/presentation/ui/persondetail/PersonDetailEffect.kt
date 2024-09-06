package com.dcs.presentation.ui.persondetail

sealed interface PersonDetailEffect {
    data class NavigateToMovieDetail(
        val movieId: Int,
    ) : PersonDetailEffect

    data class NavigateToTvShowDetail(
        val tvShowId: Int,
    ) : PersonDetailEffect

    data object NavigateUp : PersonDetailEffect
}
