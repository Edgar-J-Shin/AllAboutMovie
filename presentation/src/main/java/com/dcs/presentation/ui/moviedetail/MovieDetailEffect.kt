package com.dcs.presentation.ui.moviedetail

import com.dcs.presentation.core.designsystem.state.SnackbarState

sealed interface MovieDetailEffect {
    data object NavigateBack : MovieDetailEffect

    data class ShowSnackbar(
        val state: SnackbarState,
    ) : MovieDetailEffect
}
