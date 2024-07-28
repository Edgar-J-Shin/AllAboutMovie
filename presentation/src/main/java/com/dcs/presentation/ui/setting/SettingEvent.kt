package com.dcs.presentation.ui.setting

sealed interface SettingEvent {
    data object SignIn : SettingEvent

    data class SignOut(
        val sessionId: String,
    ) : SettingEvent
}
