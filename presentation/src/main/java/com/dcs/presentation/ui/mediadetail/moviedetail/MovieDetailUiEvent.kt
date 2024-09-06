package com.dcs.presentation.ui.mediadetail.moviedetail

sealed interface MovieDetailUiEvent {

    data object OnNavigationBackClick : MovieDetailUiEvent

    data class OnPersonClick(
        val personId: Int,
    ) : MovieDetailUiEvent
}
