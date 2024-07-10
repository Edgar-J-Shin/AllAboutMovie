package com.dcs.presentation.core.state

import java.util.Locale

enum class MovieTrendType {
    DAY, WEEK;

    override fun toString(): String {
        return super.toString().lowercase(Locale.getDefault())
    }
}