package com.dcs.presentation.ui.mediadetail.tvshowdetail

sealed interface TvShowDetailUiEvent {

    data object OnNavigationBackClick : TvShowDetailUiEvent

    data class OnPersonClick(
        val personId: Int,
    ) : TvShowDetailUiEvent
}
