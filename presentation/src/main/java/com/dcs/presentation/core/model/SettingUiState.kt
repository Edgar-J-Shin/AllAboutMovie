package com.dcs.presentation.core.model

import androidx.compose.runtime.Stable
import com.dcs.domain.model.SessionId
import com.dcs.presentation.BuildConfig

@Stable
data class SettingUiState(
    val userProfile: UserProfile,
)

sealed interface UserProfile {
    data object NotLoggedIn : UserProfile

    data class User(
        val id: Int,
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
                    includeAdult = user.includeAdult,
                    name = user.name,
                    username = user.username,
                    sessionId = user.sessionId,
                    avatarImageUrl = if (user.avatar.tmdb.value != null) {

                        "${BuildConfig.TMDB_IMAGE_URL}/w200${user.avatar.tmdb.value}"
                    } else {
                        "${BuildConfig.TMDB_GRAVATR_URL}${user.avatar.gravatar.value}.jpf?s=200"
                    },
                )
            } else {
                NotLoggedIn
            }
        }
    }
}
