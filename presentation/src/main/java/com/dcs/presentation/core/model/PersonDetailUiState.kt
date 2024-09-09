package com.dcs.presentation.core.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.dcs.domain.model.MediaContentId
import com.dcs.domain.model.PersonId
import com.dcs.presentation.BuildConfig
import com.dcs.presentation.R
import java.time.LocalDate

data class PersonDetailUiState(
    val id: PersonId,
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
    val knownFor: List<KnownForUiState>,
    val credits: Map<String, List<PersonCreditUiState>>,
    val creditCounts: Int,
    val profileImages: List<ProfileImageUiState>,
)

data class PersonCreditUiState(
    val id: MediaContentId,
    val adult: Boolean,
    val mediaType: MediaTypeUiState,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val role: String,
)

@Composable
fun PersonDetailUiState.getProfileUrl(): String =
    "${BuildConfig.TMDB_IMAGE_URL}original$profilePath"

@Composable
fun PersonCreditUiState.getActingTitle(): String {
    return if (releaseDate.isBlank()) {
        title
    } else {
        "${LocalDate.parse(releaseDate).year} $title"
    }
}

@Composable
fun PersonCreditUiState.getCharacterTitle(): AnnotatedString {
    if (role.isBlank()) return AnnotatedString("")
    return buildAnnotatedString {
        withStyle(
            SpanStyle(
                fontWeight = FontWeight.Light
            )
        ) {
            append(stringResource(id = R.string.title_as_character))
        }
        append(" ")
        append(role)
    }
}

data class ProfileImageUiState(
    val aspectRatio: Double,
    val filePath: String,
    val height: Int,
    val iso6391: String,
    val voteAverage: Double,
    val voteCount: Int,
    val width: Int,
)

fun ProfileImageUiState.getProfileImageUrl(): String =
    "${BuildConfig.TMDB_IMAGE_URL}original$filePath"
