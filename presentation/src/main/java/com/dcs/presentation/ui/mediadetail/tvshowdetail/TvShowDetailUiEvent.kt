package com.dcs.presentation.ui.mediadetail.tvshowdetail

sealed interface TvShowDetailUiEvent {

    data object NavigateBack : TvShowDetailUiEvent

    data class NavigateToPersonDetail(val personId: Int) : TvShowDetailUiEvent
}
