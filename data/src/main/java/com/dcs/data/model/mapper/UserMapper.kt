package com.dcs.data.model.mapper

import com.dcs.data.remote.model.Avatar
import com.dcs.data.remote.model.GetUserResponse
import com.dcs.domain.model.GravatarHash
import com.dcs.domain.model.SessionId
import com.dcs.domain.model.TmdbAvatarPath
import com.dcs.domain.model.User

fun GetUserResponse.toEntity(sessionId: SessionId) = User(
    id = this.id,
    name = this.name,
    username = this.username,
    avatar = this.avatar.toEntity(),
    includeAdult = this.includeAdult,
    iso31661 = this.iso31661,
    iso6391 = this.iso6391,
    sessionId = sessionId
)

private fun Avatar.toEntity() = com.dcs.domain.model.Avatar(
    gravatar = GravatarHash(this.gravatar.hash),
    tmdb = TmdbAvatarPath(this.tmdb.avatarPath)
)
