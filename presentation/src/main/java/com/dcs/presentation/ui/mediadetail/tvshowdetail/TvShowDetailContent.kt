package com.dcs.presentation.ui.mediadetail.tvshowdetail

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dcs.presentation.core.model.TvShowDetailUiState
import com.dcs.presentation.core.model.getBackdropPathUrl
import com.dcs.presentation.core.model.getCrew
import com.dcs.presentation.core.model.getGenres
import com.dcs.presentation.core.model.getNameWithFirstAirYear
import com.dcs.presentation.core.model.getOriginCountry
import com.dcs.presentation.core.model.getPosterPathUrl
import com.dcs.presentation.core.model.getRuntime
import com.dcs.presentation.core.model.getSpokenLanguage
import com.dcs.presentation.core.model.getVotePercentage
import com.dcs.presentation.ui.mediadetail.component.Casts
import com.dcs.presentation.ui.mediadetail.component.Crews
import com.dcs.presentation.ui.mediadetail.component.MediaDetailImage
import com.dcs.presentation.ui.mediadetail.component.MediaDetailInfo
import com.dcs.presentation.ui.mediadetail.component.Overview
import com.dcs.presentation.ui.mediadetail.component.Status

internal fun LazyListScope.tvShowDetailContent(
    uiState: TvShowDetailUiState,
    modifier: Modifier = Modifier,
) {
    item {
        MediaDetailImage(
            backdropPathUrl = uiState.getBackdropPathUrl(),
            posterPathUrl = uiState.getPosterPathUrl(),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f)
        )
    }

    spacer()

    item {
        MediaDetailInfo(
            title = uiState.getNameWithFirstAirYear(),
            score = uiState.getVotePercentage(),
            releaseDate = uiState.firstAirDate,
            country = uiState.getOriginCountry(),
            runtime = uiState.getRuntime(),
            genres = uiState.getGenres(),
            tagline = uiState.tagline,
            modifier = modifier.fillMaxWidth()
        )
    }

    spacer()

    item {
        Overview(
            uiState.overview,
            modifier = modifier.fillMaxWidth()
        )
    }

    spacer()

    item {
        Crews(
            uiState.getCrew(),
            modifier = modifier.fillMaxWidth()
        )
    }

    spacer()

    item {
        Casts(
            casts = uiState.credits.cast,
            modifier = Modifier.fillMaxWidth()
        )
    }

    spacer()

    item {
        Status(
            language = uiState.getSpokenLanguage(),
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

