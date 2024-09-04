package com.dcs.presentation.core.model

import androidx.compose.runtime.Stable
import com.dcs.presentation.BuildConfig
import com.dcs.presentation.core.extensions.ImageType
import java.time.LocalDate

@Stable
data class TvShowDetailUiState(
    val adult: Boolean,
    val backdropPath: String,
    val createdBy: List<CreatedByUiState>,
    val episodeRunTime: List<Int>,
    val firstAirDate: String,
    val genres: List<GenreUiState>,
    val homepage: String,
    val id: Int,
    val inProduction: Boolean,
    val languages: List<String>,
    val lastAirDate: String,
    val lastEpisodeToAir: EpisodeToAirUiState,
    val name: String,
    val networks: List<NetworkUiState>,
    val nextEpisodeToAir: EpisodeToAirUiState,
    val numberOfEpisodes: Int,
    val numberOfSeasons: Int,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val productionCompanies: List<ProductionCompanyUiState>,
    val productionCountries: List<ProductionCountryUiState>,
    val seasons: List<SeasonUiState>,
    val spokenLanguages: List<SpokenLanguageUiState>,
    val status: String,
    val tagline: String,
    val type: String,
    val voteAverage: Double,
    val voteCount: Int,
    val credits: CreditsUiState,
)

@Stable
data class CreatedByUiState(
    val creditId: String,
    val gender: Int,
    val id: Int,
    val name: String,
    val originalName: String,
    val profilePath: String,
)

@Stable
data class EpisodeToAirUiState(
    val airDate: String,
    val episodeNumber: Int,
    val episodeType: String,
    val id: Int,
    val name: String,
    val overview: String,
    val productionCode: String,
    val runtime: Int,
    val seasonNumber: Int,
    val showId: Int,
    val stillPath: String,
    val voteAverage: Double,
    val voteCount: Int,
)

@Stable
data class NetworkUiState(
    val id: Int,
    val logoPath: String,
    val name: String,
    val originCountry: String,
)

@Stable
data class SeasonUiState(
    val airDate: String,
    val episodeCount: Int,
    val id: Int,
    val name: String,
    val overview: String,
    val posterPath: String,
    val seasonNumber: Int,
    val voteAverage: Double,
)

fun TvShowDetailUiState.getPosterPathUrl(imageType: ImageType = ImageType.ORIGINAL) = "${BuildConfig.TMDB_IMAGE_URL}$imageType$posterPath"

fun TvShowDetailUiState.getBackdropPathUrl(imageType: ImageType = ImageType.ORIGINAL) = "${BuildConfig.TMDB_IMAGE_URL}$imageType$backdropPath"

fun TvShowDetailUiState.getVotePercentage() = (voteAverage * 10).toInt()

fun TvShowDetailUiState.getNameWithFirstAirYear() = "$name (${LocalDate.parse(firstAirDate).year})"

fun TvShowDetailUiState.getGenres() = genres.joinToString(separator = ",") { it.name }

fun TvShowDetailUiState.getOriginCountry() = originCountry.getOrNull(0) ?: ""

fun TvShowDetailUiState.getSpokenLanguage(): String = spokenLanguages.firstOrNull()?.name ?: ""

fun TvShowDetailUiState.getCrew() = credits.crew
    .groupBy { it.name }
    .map { crewMap ->
        crewMap.key to crewMap.value.joinToString(",") { it.job }
    }
    .take(4)
