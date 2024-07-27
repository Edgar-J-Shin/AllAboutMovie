package com.dcs.domain.model

@JvmInline
value class SessionId(
    val value: String,
) {
    init {
        require(value.isNotBlank())
    }
}
