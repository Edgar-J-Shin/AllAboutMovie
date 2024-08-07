package com.dcs.presentation.core.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.dcs.domain.model.SessionId
import com.dcs.presentation.BuildConfig
import com.dcs.presentation.R
import com.dcs.presentation.core.state.UiState

@Stable
data class SettingUiState(
    val userProfile: UserProfile,
)

sealed interface UserProfile {
    data object NotLoggedIn : UserProfile

    data class User(
        val id: Long,
        val tmdbId: Long,
        val includeAdult: Boolean,
        val name: String,
        val username: String,
        val avatarImageUrl: String,
        val sessionId: SessionId,
    ) : UserProfile

    companion object {

        /**
         * Factory function to create [UserProfile] from [com.dcs.domain.model.User].
         *
         * Ref. Avatar image URL
         * https://www.themoviedb.org/talk/6033eeaa20ecaf003e1928ef#603447190d2944003e59aea5
         */
        operator fun invoke(
            user: com.dcs.domain.model.User?,
        ): UserProfile {
            return if (user != null) {
                User(
                    id = user.id,
                    tmdbId = user.tmdbId,
                    includeAdult = user.includeAdult,
                    name = user.name,
                    username = user.username,
                    sessionId = user.sessionId,
                    avatarImageUrl = if (user.avatar.tmdb.value != null) {
                        "${BuildConfig.TMDB_IMAGE_URL}/w200${user.avatar.tmdb.value}"
                    } else {
                        "${BuildConfig.TMDB_GRAVATR_URL}${user.avatar.gravatar.value}.jpg?s=200"
                    },
                )
            } else {
                NotLoggedIn
            }
        }
    }
}

@Composable
fun UserProfile.toDisplayName() =
    if (this is UserProfile.User) {
        this.name.ifBlank { this.username }
    } else {
        stringResource(
            R.string.ask_to_login
        )
    }

class SettingUiStateProvider :
    PreviewParameterProvider<Pair<UiState<SettingUiState>, Boolean>> {
    override val values: Sequence<Pair<UiState<SettingUiState>, Boolean>>
        get() = sequenceOf(
            UiState.Loading to false,
            UiState.Success(SettingUiState(UserProfile.NotLoggedIn)) to false,
            UiState.Success(
                SettingUiState(
                    UserProfile.User(
                        id = 1,
                        tmdbId = 1,
                        includeAdult = true,
                        name = "name",
                        username = "username",
                        sessionId = SessionId("sessionId"),
                        avatarImageUrl = "https://mblogthumb-phinf.pstatic.net/MjAyMzA4MDdfMTk5/MDAxNjkxNDA5NTk2MTcz.Zr7MEQr-w3PH_l5R2uzj_rTJlPOcMZka28xz7zLJWIQg.ts6pjnQVYkBLV_fXtlV2N0_A3mRlMW-woSdq9gUoGOkg.PNG.saontsdkss119/image.png?type=w800",
                    )
                )
            ) to true,
            UiState.Success(
                SettingUiState(
                    userProfile = UserProfile.User(
                        id = 1,
                        tmdbId = 1,
                        includeAdult = true,
                        name = "name",
                        username = "username",
                        sessionId = SessionId("sessionId"),
                        avatarImageUrl = "https://mblogthumb-phinf.pstatic.net/MjAyMzA4MDdfMTk5/MDAxNjkxNDA5NTk2MTcz.Zr7MEQr-w3PH_l5R2uzj_rTJlPOcMZka28xz7zLJWIQg.ts6pjnQVYkBLV_fXtlV2N0_A3mRlMW-woSdq9gUoGOkg.PNG.saontsdkss119/image.png?type=w800",
                    ),
                )
            ) to false,
        )
}
