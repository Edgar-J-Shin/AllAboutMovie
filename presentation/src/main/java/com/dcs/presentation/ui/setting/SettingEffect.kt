package com.dcs.presentation.ui.setting

sealed interface SettingEffect {
    data object SignIn : SettingEffect
}
