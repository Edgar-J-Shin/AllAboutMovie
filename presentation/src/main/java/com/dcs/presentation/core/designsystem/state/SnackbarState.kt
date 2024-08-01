package com.dcs.presentation.core.designsystem.state

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource
import com.dcs.presentation.R

@Immutable
sealed class SnackbarState(
    val duration: SnackbarDuration = SnackbarDuration.Short,
) {

    abstract val message: CharSequence
        @Composable
        get

    data object Empty : SnackbarState() {

        override val message: CharSequence
            @Composable
            get() = ""
    }

    data object SettingToSignInError : SnackbarState() {

        override val message: CharSequence
            @Composable
            get() = stringResource(id = R.string.setting_to_sign_in_error)
    }
}

