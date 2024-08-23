package com.dcs.domain.model

import java.util.Locale

@JvmInline
value class TimeWindow private constructor(
    val value: String,
) {
    init {
        require(value.isNotBlank())
    }

    companion object {
        fun timeWindow(timeWindow: String) = TimeWindow(
            timeWindow.lowercase(Locale.getDefault())
        )
    }
}
