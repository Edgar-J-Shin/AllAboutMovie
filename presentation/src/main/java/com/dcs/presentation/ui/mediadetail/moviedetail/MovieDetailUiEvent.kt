package com.dcs.presentation.ui.mediadetail.moviedetail

sealed interface MovieDetailUiEvent {

    data object NavigateBack : MovieDetailUiEvent

    data class NavigateToPersonDetail(val personId: Int) : MovieDetailUiEvent
}
