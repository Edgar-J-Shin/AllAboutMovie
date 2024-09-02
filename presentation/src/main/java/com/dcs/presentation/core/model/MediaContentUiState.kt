package com.dcs.presentation.core.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.dcs.domain.model.MediaContentId
import com.dcs.presentation.BuildConfig
import com.dcs.presentation.core.extensions.ImageType

@Stable
data class MediaContentUiState(
    val id: Int,
    val posterPath: String,
    val voteAverage: Double,
    val title: String,
    val mediaType: String,
    val releaseDate: String,
)

fun MediaContentUiState.toMediaContentId() = MediaContentId(id)

fun MediaContentUiState.getPosterPathUrl(imageType: ImageType = ImageType.ORIGINAL) = "${BuildConfig.TMDB_IMAGE_URL}$imageType$posterPath"

fun MediaContentUiState.getVotePercentage() = (voteAverage * 10).toInt()

class MediaContentUiStateProvider : PreviewParameterProvider<PagingData<MediaContentUiState>> {

    override val values: Sequence<PagingData<MediaContentUiState>>
        /**
         * 1. Loading
         * 2. Error
         * 3. Empty Data
         * 4. Data
         */
        get() = sequenceOf(
            PagingData.from(
                data = emptyList(),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.Loading,
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false),
                )
            ),
            PagingData.from(
                data = emptyList(),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.Error(Exception("Error")),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false),
                )
            ),
            PagingData.from(
                data = emptyList(),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(true),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false),
                )
            ),
            PagingData.from(
                data = (0 until 10).map {
                    MediaContentUiState(
                        id = it,
                        mediaType = "movie",
                        posterPath = "",
                        releaseDate = "2021-11-12",
                        title = "The Accursed",
                        voteAverage = 6.072,
                    )
                },
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(true),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false),
                )
            ),
        )
}
