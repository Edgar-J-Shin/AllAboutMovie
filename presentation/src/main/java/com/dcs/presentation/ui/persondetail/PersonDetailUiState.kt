package com.dcs.presentation.ui.persondetail

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.dcs.presentation.BuildConfig
import com.dcs.presentation.core.model.GenderUiState
import com.dcs.presentation.core.model.KnownForUiState
import java.time.LocalDate

data class PersonDetailUiState(
    val id: Long,
    val adult: Boolean,
    val alsoKnownAs: List<String>,
    val biography: String,
    val birthday: String,
    val deathday: String,
    val gender: GenderUiState,
    val homepage: String,
    val imdbId: String,
    val knownForDepartment: String,
    val name: String,
    val placeOfBirth: String,
    val popularity: Double,
    val profilePath: String,
    val casts: List<CastUiState>,
    val crews: List<CrewUiState>,
    val knownFor: List<KnownForUiState>,
)

data class CastUiState(
    val id: Long,
    val adult: Boolean,
    val backdropPath: String,
    val character: String,
    val creditId: String,
    val episodeCount: Int,
    val firstAirDate: String,
    val genreIds: List<Int>,
    val mediaType: String,
    val name: String,
    val order: Int,
    val originCountry: List<String>,
    val originalLanguage: String,
    val originalName: String,
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

data class CrewUiState(
    val id: Long,
    val adult: Boolean,
    val backdropPath: String,
    val creditId: String,
    val department: String,
    val genreIds: List<Int>,
    val job: String,
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
)

val PersonDetailUiState.profileUrl: String
    @Composable get() = "${BuildConfig.TMDB_IMAGE_URL}original$profilePath"

val CastUiState.actingTitle: String
    @Composable get() = releaseDate.ifBlank { firstAirDate }
        .let { date ->
            if (date.isBlank()) {
                ""
            } else {
                LocalDate.parse(date).year.toString() + " "
            }
        } + name.ifBlank { title }

val CastUiState.characterTitle: AnnotatedString
    @Composable get() =
        character
            .takeIf { it.isNotBlank() }
            ?.let {
                buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Light
                        )
                    ) {
                        append("As ")
                    }
                    append(character)
                }
            } ?: AnnotatedString("")

val CrewUiState.productionTitle: String
    @Composable get() =
        releaseDate.takeIf { it.isNotBlank() }
            ?.let {
                LocalDate.parse(releaseDate).year.toString() +
                        " " + title.ifBlank { originalTitle }
            } ?: title.ifBlank { originalTitle }


val CrewUiState.jobTitle: AnnotatedString
    @Composable get() =
        job
            .takeIf { it.isNotBlank() }
            ?.let {
                buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontWeight = FontWeight.Light
                        )
                    ) {
                        append("As ")
                    }
                    append(job)
                }
            } ?: AnnotatedString("")

