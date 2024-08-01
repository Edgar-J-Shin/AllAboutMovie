package com.dcs.presentation.ui.signin

import com.dcs.domain.model.RequestToken

sealed interface SignInUiEvent {
    data class SignIn(val requestToken: RequestToken) : SignInUiEvent
    data object NavigateBack : SignInUiEvent
}
