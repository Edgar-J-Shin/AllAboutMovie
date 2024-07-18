package com.dcs.presentation.setting

sealed interface SettingEffect {
    data object SignIn : SettingEffect
}
