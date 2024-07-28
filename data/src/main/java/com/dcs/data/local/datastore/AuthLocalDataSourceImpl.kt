package com.dcs.data.local.datastore

import com.dcs.domain.model.Avatar
import com.dcs.domain.model.GravatarHash
import com.dcs.domain.model.SessionId
import com.dcs.domain.model.TmdbAvatarPath
import com.dcs.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthLocalDataSourceImpl @Inject constructor() : AuthLocalDataSource {
    override fun getUser(): Flow<User?> {
        return flow {
            val user = User(
                avatar = Avatar(
                    gravatar = GravatarHash(""),
                    tmdb = TmdbAvatarPath(""),
                ),
                id = 0,
                includeAdult = false,
                iso31661 = "",
                iso6391 = "",
                name = "",
                username = "",
                sessionId = SessionId(""),
            )
            emit(user)
        }
    }
}
