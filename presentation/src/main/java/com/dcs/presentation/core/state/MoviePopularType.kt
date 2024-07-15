package com.dcs.presentation.core.state

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dcs.presentation.R

enum class MoviePopularType {
    TV,
    MOVIE;

    @Composable
    fun toUiString() = when (this) {
        TV -> stringResource(id = R.string.movie_popular_type_tv)
        MOVIE -> stringResource(id = R.string.movie_popular_type_movie)
    }
}
