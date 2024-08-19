package com.dcs.presentation.ui.trend

import com.dcs.presentation.core.designsystem.state.SnackbarState

sealed interface TrendEffect {

    data class ShowSnackbar(
        val state: SnackbarState,
    ) : TrendEffect
}
