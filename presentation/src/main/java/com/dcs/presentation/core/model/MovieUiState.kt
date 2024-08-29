package com.dcs.presentation.core.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.dcs.domain.model.MovieId
import com.dcs.presentation.BuildConfig
import com.dcs.presentation.core.extensions.ImageType

@Stable
data class MovieUiState(
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val mediaType: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
    val name: String,
    val originalName: String,
    val originalCountry: List<String>,
    val firstAirDate: String,
)

fun MovieUiState.getPosterPathUrl(imageType: ImageType = ImageType.ORIGINAL) = "${BuildConfig.TMDB_IMAGE_URL}$imageType$posterPath"

fun MovieUiState.getBackdropPathUrl(imageType: ImageType = ImageType.ORIGINAL) = "${BuildConfig.TMDB_IMAGE_URL}$imageType$backdropPath"

fun MovieUiState.toMovieId() = MovieId(id)

fun MovieUiState.getVotePercentage() = (voteAverage * 10).toInt()

fun MovieUiState.getTitleOrName() = title.ifEmpty { name }

fun MovieUiState.getReleaseDateOrFirstAirDate() = releaseDate.ifEmpty { firstAirDate }

class MovieUiStateProvider : PreviewParameterProvider<PagingData<MovieUiState>> {

    override val values: Sequence<PagingData<MovieUiState>>
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
                    MovieUiState(
                        adult = false,
                        backdropPath = "https://image.tmdb.org/t/p/w500/jZXvRmQTAFmNaHSyN8DQqS5IIaM.jpg",
                        genreIds = listOf(27),
                        id = it,
                        mediaType = "movie",
                        originalLanguage = "en",
                        originalTitle = "The Accursed",
                        overview = "Hana spends twenty years suppressing a maleficent curse that was placed upon her bloodline, only to have a family member knowingly release it forcing her to kill or to be killed.",
                        popularity = 36.204,
                        posterPath = "",
                        releaseDate = "2021-11-12",
                        title = "The Accursed",
                        video = false,
                        voteAverage = 6.072,
                        voteCount = 97,
                        name = "",
                        originalName = "",
                        originalCountry = emptyList(),
                        firstAirDate = "2001-09-05"
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

