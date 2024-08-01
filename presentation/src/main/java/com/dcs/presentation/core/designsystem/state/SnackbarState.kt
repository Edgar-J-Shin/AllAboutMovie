package com.dcs.presentation.core.designsystem.state

import androidx.annotation.StringRes
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Immutable
import com.dcs.presentation.R

@Immutable
sealed class SnackbarState(
    @StringRes val messageResId: Int,
    val duration: SnackbarDuration = SnackbarDuration.Short,
) {

    data object SettingToSignInError :
        SnackbarState(messageResId = R.string.setting_to_sign_in_error)
}

