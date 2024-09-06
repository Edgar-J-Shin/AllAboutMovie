package com.dcs.presentation.core.model

import androidx.compose.runtime.Composable
import com.dcs.domain.model.MediaContentId
import com.dcs.presentation.BuildConfig

data class KnownForUiState(
    val id: MediaContentId,
    val name: String,
    val originalName: String,
    val adult: Boolean,
    val backdropPath: String,
    val firstAirDate: String,
    val genreIds: List<Int>,
    val mediaType: MediaTypeUiState,
    val originCountry: List<String>,
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
)

@Composable
fun KnownForUiState.getPosterUrl(): String =
    "${BuildConfig.TMDB_IMAGE_URL}original$posterPath"
