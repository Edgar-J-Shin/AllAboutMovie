package com.dcs.presentation.ui.moviedetail

sealed interface MovieDetailUiEvent {

    data object NavigateBack : MovieDetailUiEvent
}
