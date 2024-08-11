package com.dcs.presentation.core.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData

@Stable
data class MovieItemUiState(
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

class MovieItemUiStateProvider : PreviewParameterProvider<PagingData<MovieItemUiState>> {

    override val values: Sequence<PagingData<MovieItemUiState>>
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
                    MovieItemUiState(
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

