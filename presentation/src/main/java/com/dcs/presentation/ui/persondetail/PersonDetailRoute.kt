package com.dcs.presentation.ui.persondetail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dcs.presentation.core.designsystem.widget.ErrorScreen
import com.dcs.presentation.core.designsystem.widget.LoadingDialog
import com.dcs.presentation.core.model.CastUiState
import com.dcs.presentation.core.model.CrewUiState
import com.dcs.presentation.core.model.GenderUiState
import com.dcs.presentation.core.model.KnownForUiState
import com.dcs.presentation.core.model.PersonDetailUiState
import com.dcs.presentation.core.ui.state.UiState
import com.dcs.presentation.core.theme.AllAboutMovieTheme

@Composable
fun PersonDetailRoute(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PersonDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PersonDetailScreen(
        navigateUp = navigateUp,
        uiState = uiState,
        modifier = modifier.fillMaxSize(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PersonDetailScreen(
    navigateUp: () -> Unit,
    uiState: UiState<PersonDetailUiState>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 30.dp),
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        item {
            CenterAlignedTopAppBar(
                title = {
                    if (uiState is UiState.Success) {
                        val personDetailUiState = uiState.data
                        Text(
                            text = personDetailUiState.name,
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                },

                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        when (uiState) {
            is UiState.Loading -> {
                item { LoadingDialog() }
            }

            is UiState.Error -> {
                item {
                    ErrorScreen()
                }
            }

            is UiState.Success -> {
                val personDetailUiState = uiState.data
                personDetailContent(
                    personDetailUiState = personDetailUiState,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PersonDetailScreenPreview(
    @PreviewParameter(LoremIpsum::class) text: String,
) {
    AllAboutMovieTheme {
        val uiState = PersonDetailUiState(
            id = 1,
            adult = false,
            alsoKnownAs = listOf("alsoKnownAs"),
            biography = LoremIpsum(100).values.first(),
            birthday = "2021-01-01",
            deathday = "",
            gender = GenderUiState.MALE,
            homepage = "https://google.com",
            imdbId = 123456.toString(),
            knownForDepartment = "Acting",
            name = "Name",
            placeOfBirth = "Korea",
            popularity = 1.0,
            profilePath = "/profilePath",
            casts = listOf(
                CastUiState(
                    id = 0,
                    adult = false,
                    backdropPath = "",
                    character = "",
                    creditId = "",
                    episodeCount = 0,
                    firstAirDate = "",
                    genreIds = listOf(),
                    mediaType = "",
                    name = "Ayo",
                    order = 0,
                    originCountry = listOf(),
                    originalLanguage = "",
                    originalName = "",
                    originalTitle = "11111",
                    overview = "",
                    popularity = 0.0,
                    posterPath = "",
                    releaseDate = "",
                    title = "wowowowowo",
                    video = false,
                    voteAverage = 0.0,
                    voteCount = 0

                )
            ),
            crews = listOf(
                CrewUiState(
                    id = 0,
                    adult = false,
                    backdropPath = "",
                    creditId = "",
                    department = "",
                    genreIds = listOf(),
                    job = "Producer",
                    mediaType = "",
                    originalLanguage = "",
                    originalTitle = "Ayo",
                    overview = "",
                    popularity = 0.0,
                    posterPath = "",
                    releaseDate = "",
                    title = "12345",
                    video = false,
                    voteAverage = 0.0,
                    voteCount = 0
                )
            ),
            knownFor = listOf(
                KnownForUiState(
                    id = 0,
                    name = "good",
                    originalName = "or",
                    adult = false,
                    backdropPath = "b",
                    firstAirDate = "",
                    genreIds = listOf(),
                    mediaType = "",
                    originCountry = listOf(),
                    originalLanguage = "",
                    originalTitle = "GOOD",
                    overview = "",
                    popularity = 0.0,
                    posterPath = "",
                    releaseDate = "",
                    title = "title",
                    video = false,
                    voteAverage = 0.0,
                    voteCount = 0
                )
            ),
        )
        PersonDetailScreen(
            navigateUp = {},
            uiState = UiState.Success(uiState),
        )
    }
}

