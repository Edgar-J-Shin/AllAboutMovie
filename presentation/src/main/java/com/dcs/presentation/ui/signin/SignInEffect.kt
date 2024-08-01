package com.dcs.presentation.ui.signin

sealed interface SignInEffect {
    data object NavigateBack : SignInEffect
}
