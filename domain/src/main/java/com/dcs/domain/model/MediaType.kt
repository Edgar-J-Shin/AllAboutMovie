package com.dcs.domain.model

enum class MediaType(val value: String) {
    MOVIE("movie"),
    TV_SHOW("tv");

    companion object {
        fun toMediaType(mediaType: String): MediaType =
            MediaType::value.findBy(
                value = mediaType,
                defaultValue = MOVIE
            )
    }
}

inline fun <reified E : Enum<E>, V> ((E) -> V).findBy(value: V, defaultValue: E): E {
    return enumValues<E>().firstOrNull { this(it) == value } ?: defaultValue
}

