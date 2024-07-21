package com.dcs.presentation.setting

sealed interface SettingEvent {
    data object SignIn : SettingEvent
}
