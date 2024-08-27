package com.dcs.presentation.ui.moviedetail

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dcs.presentation.core.model.MovieDetailUiState
import com.dcs.presentation.core.model.getBackdropPathUrl
import com.dcs.presentation.core.model.getCrew
import com.dcs.presentation.core.model.getGenres
import com.dcs.presentation.core.model.getPosterPathUrl
import com.dcs.presentation.core.model.getRuntime
import com.dcs.presentation.core.model.getSpokenLanguage
import com.dcs.presentation.core.model.getTitleWithReleaseYear
import com.dcs.presentation.core.model.getVotePercentage
import com.dcs.presentation.ui.moviedetail.component.Casts
import com.dcs.presentation.ui.moviedetail.component.Crews
import com.dcs.presentation.ui.moviedetail.component.MovieImage
import com.dcs.presentation.ui.moviedetail.component.MovieInfo
import com.dcs.presentation.ui.moviedetail.component.Overview
import com.dcs.presentation.ui.moviedetail.component.Status

internal fun LazyListScope.movieDetailContent(
    movieDetailUiState: MovieDetailUiState,
    modifier: Modifier = Modifier,
) {
    item {
        MovieImage(
            backdropPathUrl = movieDetailUiState.getBackdropPathUrl(),
            posterPathUrl = movieDetailUiState.getPosterPathUrl(),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f)
        )
    }

    spacer()

    item {
        MovieInfo(
            title = movieDetailUiState.getTitleWithReleaseYear(),
            score = movieDetailUiState.getVotePercentage(),
            releaseDate = movieDetailUiState.releaseDate,
            country = movieDetailUiState.originCountry[0],
            runtime = movieDetailUiState.getRuntime(),
            genres = movieDetailUiState.getGenres(),
            tagline = movieDetailUiState.tagline,
            modifier = modifier.fillMaxWidth()
        )
    }

    spacer()

    item {
        Overview(
            movieDetailUiState.overview,
            modifier = modifier.fillMaxWidth()
        )
    }

    spacer()

    item {
        Crews(
            movieDetailUiState.getCrew(),
            modifier = modifier.fillMaxWidth()
        )
    }

    spacer()

    item {
        Casts(
            casts = movieDetailUiState.credits.cast,
            modifier = Modifier.fillMaxWidth()
        )
    }

    spacer()

    item {
        Status(
            language = movieDetailUiState.getSpokenLanguage(),
            budget = movieDetailUiState.budget,
            revenue = movieDetailUiState.revenue,
            modifier = modifier.fillMaxWidth()
        )
    }

    spacer()
}

internal fun LazyListScope.spacer(height: Dp = 20.dp) {
    item {
        Spacer(modifier = Modifier.height(height))
    }
}

