package com.dcs.presentation.ui.signin

import com.dcs.domain.model.RequestToken

sealed interface SignInEvent {
    data class SignIn(val requestToken: RequestToken) : SignInEvent
}
