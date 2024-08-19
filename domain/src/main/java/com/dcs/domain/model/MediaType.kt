package com.dcs.domain.model

import java.util.Locale

@JvmInline
value class MediaType private constructor(
    val value: String,
) {
    init {
        require(value.isNotBlank())
    }

    companion object {
        fun mediaType(mediaType: String) = MediaType(
            mediaType.lowercase(Locale.getDefault())
        )
    }
}
