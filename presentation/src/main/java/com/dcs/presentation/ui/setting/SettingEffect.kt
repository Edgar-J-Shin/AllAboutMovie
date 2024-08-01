package com.dcs.presentation.ui.setting

import com.dcs.domain.model.RequestToken

sealed interface SettingEffect {
    data class SignIn(
        val requestToken: RequestToken,
    ) : SettingEffect
}
