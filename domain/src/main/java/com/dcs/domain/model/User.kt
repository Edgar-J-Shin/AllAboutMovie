package com.dcs.domain.model


data class User(
    val id: Long = 0,
    val avatar: Avatar,
    val tmdbId: Long,
    val includeAdult: Boolean,
    val iso31661: String,
    val iso6391: String,
    val name: String,
    val username: String,
    val sessionId: SessionId,
)

data class Avatar(
    val gravatar: GravatarHash,
    val tmdb: TmdbAvatarPath,
)

@JvmInline
value class GravatarHash(
    val value: String,
)

@JvmInline
value class TmdbAvatarPath(
    val value: String?,
)
