package com.dcs.presentation.core.state

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dcs.presentation.R

enum class MovieTrendType {
    DAY,
    WEEK;

    @Composable
    fun toUiString() = when (this) {
        DAY -> stringResource(id = R.string.movie_trend_type_day)
        WEEK -> stringResource(id = R.string.movie_trend_type_week)
    }
}
