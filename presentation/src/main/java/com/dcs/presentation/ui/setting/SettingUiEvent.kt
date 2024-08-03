package com.dcs.presentation.ui.setting

import com.dcs.domain.model.SessionId

sealed interface SettingUiEvent {
    data object SignIn : SettingUiEvent

    data class SignOut(
        val sessionId: SessionId,
    ) : SettingUiEvent
}
