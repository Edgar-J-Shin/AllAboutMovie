package com.dcs.presentation.core.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.dcs.presentation.BuildConfig
import com.dcs.presentation.R

data class PersonUiState(
    val id: Int,
    val name: String,
    val originalName: String,
    val adult: Boolean,
    val gender: GenderUiState,
    val knownFor: List<KnownForUiState>,
    val knownForDepartment: String,
    val popularity: Double,
    val profilePath: String,
)

data class KnownForUiState(
    val id: Int,
    val name: String,
    val originalName: String,
    val adult: Boolean,
    val backdropPath: String,
    val firstAirDate: String,
    val genreIds: List<Int>,
    val mediaType: String,
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

enum class GenderUiState {
    NOT_SPECIFIED,
    FEMALE,
    MALE,
    NON_BINARY,
}

@Composable
fun GenderUiState.toGenderString(): String = when (this) {
    GenderUiState.NOT_SPECIFIED -> R.string.gender_not_specified
    GenderUiState.FEMALE -> R.string.gender_female
    GenderUiState.MALE -> R.string.gender_male
    GenderUiState.NON_BINARY -> R.string.gender_non_binary
}.let {
    stringResource(id = it)
}

@Composable
fun PersonUiState.getKnownForTitle(): String {
    if (knownFor.isEmpty()) {
        return ""
    }
    return knownFor
        .filterNot { it.title.isBlank() && it.originalTitle.isBlank() }
        .take(3)
        .joinToString { it.title.ifBlank { it.originalTitle } }
}

@Composable
fun KnownForUiState.getPosterUrl(): String =
    "${BuildConfig.TMDB_IMAGE_URL}original$posterPath"

@Composable
fun PersonUiState.getProfileUrl(): String =
    "${BuildConfig.TMDB_IMAGE_URL}original$profilePath"

class PersonUiStateProvider : PreviewParameterProvider<PagingData<PersonUiState>> {
    override val values: Sequence<PagingData<PersonUiState>>
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
                data = (0 until 10).map {
                    PersonUiState(
                        id = it,
                        name = "Person $it",
                        originalName = "Original Person $it",
                        adult = false,
                        gender = GenderUiState.MALE,
                        popularity = 1.0,
                        profilePath = "https://image.tmdb.org/t/p/w500/4q2NNj4S5c5cUfzjXk5jXgefBvS.jpg",
                        knownFor = emptyList(),
                        knownForDepartment = "Department $it",
                    )
                },
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(true),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false),
                )
            ),
            PagingData.from(
                data = (0 until 10).map {
                    PersonUiState(
                        id = it,
                        name = "Person $it",
                        originalName = "Original Person $it",
                        adult = false,
                        gender = GenderUiState.FEMALE,
                        popularity = 1.0,
                        profilePath = "https://image.tmdb.org/t/p/w500/4q2NNj4S5c5cUfzjXk5jXgefBvS.jpg",
                        knownFor = (0 until 5).map { idx ->
                            KnownForUiState(
                                id = idx,
                                originalName = "Original Name $idx",
                                name = "Name $idx",
                                adult = false,
                                backdropPath = "https://image.tmdb.org/t/p/w500/4q2NNj4S5c5cUfzjXk5jXgefBvS.jpg",
                                firstAirDate = "2021-09-01",
                                genreIds = emptyList(),
                                mediaType = "movie",
                                originCountry = emptyList(),
                                originalLanguage = "en",
                                originalTitle = "Original Title $idx",
                                overview = "Overview $it",
                                popularity = 1.0,
                                posterPath = "https://image.tmdb.org/t/p/w500/4q2NNj4S5c5cUfzjXk5jXgefBvS.jpg",
                                releaseDate = "2021-09-01",
                                title = "Title $idx",
                                video = false,
                                voteAverage = 1.0,
                                voteCount = 1,
                            )
                        },
                        knownForDepartment = "Department $it",
                    )
                },
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(true),
                    prepend = LoadState.NotLoading(false),
                    append = LoadState.NotLoading(false),
                )
            )
        )
}
