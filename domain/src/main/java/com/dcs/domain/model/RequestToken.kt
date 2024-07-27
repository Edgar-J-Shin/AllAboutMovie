package com.dcs.domain.model

@JvmInline
value class RequestToken(
    val value: String,
) {
    init {
        require(value.isNotBlank())
    }
}
