package com.dcs.presentation.ui.setting

import com.dcs.domain.model.SessionId

sealed interface SettingEvent {
    data object SignIn : SettingEvent

    data class SignOut(
        val sessionId: SessionId,
    ) : SettingEvent
}
