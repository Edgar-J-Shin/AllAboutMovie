package com.dcs.presentation.core.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.dcs.presentation.BuildConfig
import com.dcs.presentation.core.extensions.ImageType
import com.dcs.presentation.core.ui.state.UiState
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

fun TvShowDetailUiState.getRuntime(): String = episodeRunTime.firstOrNull()
    ?.let { runtime -> "${runtime / 60}h ${runtime % 60}m" } ?: ""

fun TvShowDetailUiState.getCrew() = credits.crew
    .groupBy { it.name }
    .map { crewMap ->
        crewMap.key to crewMap.value.joinToString(",") { it.job }
    }
    .take(4)

class TvShowDetailUiStateProvider : PreviewParameterProvider<UiState<TvShowDetailUiState>> {

    private val episodeToAirUiState = EpisodeToAirUiState(
        airDate = "",
        episodeNumber = 1,
        episodeType = "Scripted",
        id = 1234,
        name = "",
        overview = "",
        productionCode = "",
        runtime = 31,
        seasonNumber = 2,
        showId = 1,
        stillPath = "",
        voteAverage = 5.332,
        voteCount = 21,
    )

    private val tvShowDetailUiState = TvShowDetailUiState(
        adult = false,
        backdropPath = "https://image.tmdb.org/t/p/w500/jZXvRmQTAFmNaHSyN8DQqS5IIaM.jpg",
        createdBy = emptyList(),
        episodeRunTime = listOf(90, 31, 132),
        firstAirDate = "2020-11-29",
        genres = emptyList(),
        homepage = "",
        id = 123,
        inProduction = true,
        languages = listOf("af", "en"),
        lastAirDate = "2024-08-28",
        lastEpisodeToAir = episodeToAirUiState,
        name = "The Accursed",
        networks = emptyList(),
        nextEpisodeToAir = episodeToAirUiState,
        numberOfEpisodes = 2,
        numberOfSeasons = 3,
        originCountry = listOf("ZA"),
        originalLanguage = "af",
        originalName = "Binnelanders",
        overview = "A South African Afrikaans soap opera. It is set in and around the fictional private hospital, Binneland Kliniek, in Pretoria, and the storyline follows the trials, trauma and tribulations of the staff and patients of the hospital.",
        popularity = 3978.961,
        posterPath = "/v9nGSRx5lFz6KEgfmgHJMSgaARC.jpg",
        productionCompanies = emptyList(),
        productionCountries = emptyList(),
        seasons = emptyList(),
        spokenLanguages = emptyList(),
        status = "Returning Series",
        tagline = "",
        type = "Scripted",
        voteAverage = 5.632,
        voteCount = 73,
        credits = CreditsUiState(
            cast = emptyList(),
            crew = emptyList()
        )
    )

    private val crew = CreditsCrewUiState(
        adult = false,
        gender = 1,
        id = 1683343,
        knownForDepartment = "Acting",
        name = "Cailee Spaeny",
        originalName = "Cailee Spaeny",
        popularity = 90.073,
        profilePath = "/nquUc6o2dK4Pg4zjvl2HmZOfiRS.jpg",
        creditId = "4",
        department = "Directing",
        job = "Director",
    )

    private val cast = CreditsCastUiState(
        adult = false,
        gender = 1,
        id = 1683343,
        knownForDepartment = "Acting",
        name = "Cailee Spaeny",
        originalName = "Cailee Spaeny",
        popularity = 90.073,
        profilePath = "/nquUc6o2dK4Pg4zjvl2HmZOfiRS.jpg",
        castId = 4,
        character = "Rain",
        creditId = "63904e44bc8abc13d787a6aa",
        order = 0
    )

    override val values: Sequence<UiState<TvShowDetailUiState>>
        /**
         * 1. Loading
         * 2. Error
         * 3. Success
         * 4. Success with crew, cast
         */
        get() = sequenceOf(
            UiState.Loading,
            UiState.Error(Exception()),
            UiState.Success(tvShowDetailUiState),
            UiState.Success(
                tvShowDetailUiState.copy(
                    credits = CreditsUiState(
                        crew = listOf(
                            crew,
                            crew.copy(name = "Cailee Spaeny", job = "Screenplay"),
                            crew.copy(name = "Fede Álvarez", job = "Writer"),
                            crew.copy(name = "Ronald Shusett", job = "Characters"),
                            crew.copy(name = "Rodo Sayagues", job = "Writer")
                        ),
                        cast = listOf(
                            cast, cast, cast, cast, cast
                        )
                    )
                )
            )
        )
}
