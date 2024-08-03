package com.dcs.presentation.ui.setting

import com.dcs.domain.model.RequestToken
import com.dcs.presentation.core.designsystem.state.SnackbarState

sealed interface SettingEffect {
    data class SignIn(
        val requestToken: RequestToken,
    ) : SettingEffect

    data class ShowSnackbar(
        val state: SnackbarState,
    ) : SettingEffect
}
