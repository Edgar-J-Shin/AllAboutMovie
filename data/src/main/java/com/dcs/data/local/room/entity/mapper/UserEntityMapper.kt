package com.dcs.data.local.room.entity.mapper

import com.dcs.data.local.room.entity.User

fun User.toEntity() = com.dcs.domain.model.User(
    id = tmdbId,
    name = name,
    username = username,
    includeAdult = includeAdult,
    iso31661 = iso31661,
    iso6391 = iso6391,
    avatar = com.dcs.domain.model.Avatar(
        gravatar = com.dcs.domain.model.GravatarHash(avatar.gravatarHash),
        tmdb = com.dcs.domain.model.TmdbAvatarPath(avatar.tmdbUrl),
    ),
    sessionId = com.dcs.domain.model.SessionId(sessionId),
)

fun com.dcs.domain.model.User.toLocalData() = User(
    tmdbId = id,
    name = name,
    username = username,
    includeAdult = includeAdult,
    iso31661 = iso31661,
    iso6391 = iso6391,
    avatar = com.dcs.data.local.room.entity.Avatar(
        gravatarHash = avatar.gravatar.value,
        tmdbUrl = avatar.tmdb.value,
    ),
    sessionId = sessionId.value,
)
