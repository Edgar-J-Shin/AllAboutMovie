package com.dcs.presentation.ui.tvshowdetail

sealed interface TvShowDetailUiEvent {

    data object NavigateBack : TvShowDetailUiEvent
}
