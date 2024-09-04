package com.dcs.presentation.ui.mediadetail.moviedetail

sealed interface MovieDetailUiEvent {

    data object NavigateBack : MovieDetailUiEvent
}
