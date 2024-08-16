package com.dcs.presentation.ui.home

import com.dcs.presentation.core.designsystem.state.SnackbarState

sealed interface HomeEffect {

    data class NavigateToSearchResult(
        val keyword: String,
    ) : HomeEffect

    data class ShowSnackbar(
        val state: SnackbarState,
    ) : HomeEffect
}
