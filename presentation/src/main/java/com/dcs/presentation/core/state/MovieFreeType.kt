package com.dcs.presentation.core.state

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.dcs.presentation.R

enum class MovieFreeType {
    MOVIE,
    TV;

    @Composable
    fun toUiString() = when (this) {
        MOVIE -> stringResource(id = R.string.movie_free_type_movie)
        TV -> stringResource(id = R.string.movie_free_type_tv)
    }
}
