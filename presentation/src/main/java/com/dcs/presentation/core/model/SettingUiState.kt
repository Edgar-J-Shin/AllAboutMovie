package com.dcs.presentation.core.model

import androidx.compose.runtime.Stable
import com.dcs.domain.model.User

@Stable
data class SettingUiState(
    val userProfile: UserProfile,
)

sealed interface UserProfile {
    data object NotLoggedIn : UserProfile

    data class LoggedIn(
        val user: User,
    ) : UserProfile
}
