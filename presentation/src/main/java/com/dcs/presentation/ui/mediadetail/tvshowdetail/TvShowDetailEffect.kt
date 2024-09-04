package com.dcs.presentation.ui.mediadetail.tvshowdetail

import com.dcs.presentation.core.designsystem.state.SnackbarState

sealed interface TvShowDetailEffect {
    data object NavigateBack : TvShowDetailEffect

    data class NavigateToPersonDetail(val personId: Int) : TvShowDetailEffect

    data class ShowSnackbar(
        val state: SnackbarState,
    ) : TvShowDetailEffect
}
