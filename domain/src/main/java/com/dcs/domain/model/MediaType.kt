package com.dcs.domain.model

enum class MediaType(val value: String) {
    MOIVE("movie"),
    TV_SHOW("tv")
}

inline fun <reified E : Enum<E>, V> ((E) -> V).findBy(value: V, defaultValue: E): E {
    return enumValues<E>().firstOrNull { this(it) == value } ?: defaultValue
}

