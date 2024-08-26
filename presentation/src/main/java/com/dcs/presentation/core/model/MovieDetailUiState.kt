package com.dcs.presentation.core.model

import androidx.compose.runtime.Stable
import com.dcs.domain.model.MovieId
import com.dcs.presentation.BuildConfig
import com.dcs.presentation.core.extensions.ImageType

@Stable
data class MovieDetailUiState(
    val adult: Boolean,
    val backdropPath: String,
    val belongsToCollection: BelongsToCollectionUiState,
    val budget: Int,
    val credits: CreditsUiState,
    val genres: List<GenreUiState>,
    val homepage: String,
    val id: Int,
    val imdbId: String,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<ProductionCompanyUiState>,
    val productionCountries: List<ProductionCountryUiState>,
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val spokenLanguages: List<SpokenLanguageUiState>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,
)

data class BelongsToCollectionUiState(
    val backdropPath: String,
    val id: Int,
    val name: String,
    val posterPath: String,
)

data class CreditsUiState(
    val cast: List<CastUiState>,
    val crew: List<CrewUiState>,
)

data class CastUiState(
    val adult: Boolean,
    val castId: Int,
    val character: String,
    val creditId: String,
    val gender: Int,
    val id: Int,
    val knownForDepartment: String,
    val name: String,
    val order: Int,
    val originalName: String,
    val popularity: Double,
    val profilePath: String,
)

data class CrewUiState(
    val adult: Boolean,
    val creditId: String,
    val department: String,
    val gender: Int,
    val id: Int,
    val job: String,
    val knownForDepartment: String,
    val name: String,
    val originalName: String,
    val popularity: Double,
    val profilePath: String,
)

data class GenreUiState(
    val id: Int,
    val name: String,
)

data class ProductionCompanyUiState(
    val id: Int,
    val logoPath: String,
    val name: String,
    val originCountry: String,
)

data class ProductionCountryUiState(
    val iso31661: String,
    val name: String,
)

data class SpokenLanguageUiState(
    val englishName: String,
    val iso6391: String,
    val name: String,
)


fun MovieDetailUiState.getPosterPathUrl(imageType: ImageType = ImageType.ORIGINAL) = "${BuildConfig.TMDB_IMAGE_URL}$imageType$posterPath"

fun MovieDetailUiState.getBackdropPathUrl(imageType: ImageType = ImageType.ORIGINAL) = "${BuildConfig.TMDB_IMAGE_URL}$imageType$backdropPath"

fun MovieDetailUiState.toMovieId() = MovieId(id)

fun MovieDetailUiState.getVotePercentage() = (voteAverage * 10).toInt()

fun MovieDetailUiState.getTitleOrNameWithReleaseYear() = "$title (${releaseDate.substring(0, 4)})"
