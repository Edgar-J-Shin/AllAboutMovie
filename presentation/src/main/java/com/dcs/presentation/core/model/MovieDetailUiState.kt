package com.dcs.presentation.core.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.dcs.domain.model.MovieId
import com.dcs.presentation.BuildConfig
import com.dcs.presentation.core.extensions.ImageType
import com.dcs.presentation.core.ui.common.UiState
import java.time.LocalDate

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
    val cast: List<CreditsCastUiState>,
    val crew: List<CreditsCrewUiState>,
)

data class CreditsCastUiState(
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

data class CreditsCrewUiState(
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

fun MovieDetailUiState.getTitleWithReleaseYear() = "$title (${LocalDate.parse(releaseDate).year})"

fun MovieDetailUiState.getGenres() = genres.joinToString(separator = ",") { it.name }

fun MovieDetailUiState.getRuntime() = "${runtime / 60}h ${runtime % 60}m"

fun MovieDetailUiState.getOriginCountry() = originCountry.getOrNull(0) ?: ""

fun MovieDetailUiState.getSpokenLanguage(): String = spokenLanguages.firstOrNull()?.name ?: ""

fun MovieDetailUiState.getCrew() = credits.crew
    .groupBy { it.name }
    .map { crewMap ->
        crewMap.key to crewMap.value.joinToString(",") { it.job }
    }
    .take(4)

fun CreditsCastUiState.getProfilePathUrl(imageType: ImageType = ImageType.ORIGINAL) = "${BuildConfig.TMDB_IMAGE_URL}$imageType$profilePath"


class MovieDetailUiStateProvider : PreviewParameterProvider<UiState<MovieDetailUiState>> {

    private val movieDetailUiState = MovieDetailUiState(
        adult = false,
        backdropPath = "https://image.tmdb.org/t/p/w500/jZXvRmQTAFmNaHSyN8DQqS5IIaM.jpg",
        belongsToCollection = BelongsToCollectionUiState(
            backdropPath = "",
            posterPath = "",
            id = 0,
            name = ""
        ),
        budget = 1000,
        credits = CreditsUiState(
            cast = emptyList(),
            crew = emptyList()
        ),
        genres = emptyList(),
        homepage = "",
        id = 123,
        imdbId = "456",
        originCountry = emptyList(),
        originalLanguage = "en",
        originalTitle = "The Accursed",
        overview = "Hana spends twenty years suppressing a maleficent curse that was placed upon her bloodline, only to have a family member knowingly release it forcing her to kill or to be killed.",
        popularity = 36.204,
        posterPath = "https://image.tmdb.org/t/p/w500/jZXvRmQTAFmNaHSyN8DQqS5IIaM.jpg",
        productionCompanies = emptyList(),
        productionCountries = emptyList(),
        releaseDate = "2021-11-12",
        revenue = 0,
        runtime = 169,
        spokenLanguages = listOf(
            SpokenLanguageUiState(
                name = "english",
                iso6391 = "",
                englishName = ""
            )
        ),
        status = "status",
        tagline = "tagline",
        title = "The Accursed",
        video = false,
        voteAverage = 6.072,
        voteCount = 97,
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

    override val values: Sequence<UiState<MovieDetailUiState>>
        /**
         * 1. Loading
         * 2. Error
         * 3. Success
         * 4. Success with crew, cast
         */
        get() = sequenceOf(
            UiState.Loading,
            UiState.Error(Exception()),
            UiState.Success(movieDetailUiState),
            UiState.Success(
                movieDetailUiState.copy(
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
