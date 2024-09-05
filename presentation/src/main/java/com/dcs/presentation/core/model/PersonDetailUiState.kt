package com.dcs.presentation.core.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.dcs.domain.model.MediaContentId
import com.dcs.presentation.BuildConfig
import com.dcs.presentation.R
import java.time.LocalDate

data class PersonDetailUiState(
    val id: Int,
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
    val id: MediaContentId,
    val adult: Boolean,
    val backdropPath: String,
    val character: String,
    val creditId: String,
    val episodeCount: Int,
    val firstAirDate: String,
    val genreIds: List<Int>,
    val mediaType: MediaTypeUiState,
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
    val id: MediaContentId,
    val adult: Boolean,
    val backdropPath: String,
    val creditId: String,
    val department: String,
    val genreIds: List<Int>,
    val job: String,
    val mediaType: MediaTypeUiState,
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
    val originalName: String,
    val name: String,
    val firstAirDate: String,
    val episodeCount: Int,
    val originCountry: List<String>,
)

@Composable
fun PersonDetailUiState.getProfileUrl(): String =
    "${BuildConfig.TMDB_IMAGE_URL}original$profilePath"

@Composable
fun CastUiState.getActingTitle(): String {
    val title = name.ifBlank { title }
    val date = releaseDate.ifBlank { firstAirDate }
    return if (date.isBlank()) {
        title
    } else {
        "${LocalDate.parse(date).year} $title"
    }
}

@Composable
fun CastUiState.getCharacterTitle(): AnnotatedString {
    if (character.isBlank()) return AnnotatedString("")
    return buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontWeight = FontWeight.Light
            )
        ) {
            append(stringResource(id = R.string.title_as_character))
        }
        append(" ")
        append(character)
    }
}

@Composable
fun CrewUiState.getProductionTitle(): String {
    val title = name.ifBlank { title }
    val date = releaseDate.ifBlank { firstAirDate }
    return if (date.isBlank()) {
        title
    } else {
        "${LocalDate.parse(date).year} $title"
    }
}

@Composable
fun CrewUiState.getJobTitle(): AnnotatedString {
    if (job.isBlank()) return AnnotatedString("")
    return buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontWeight = FontWeight.Light
            )
        ) {
            append(stringResource(id = R.string.title_as_job))
        }
        append(" ")
        append(job)
    }
}
