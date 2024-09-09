package com.dcs.presentation.ui.persondetail

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dcs.domain.model.MediaContentId
import com.dcs.domain.model.PersonId
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.component.ErrorScreen
import com.dcs.presentation.core.designsystem.component.NavigationBackButton
import com.dcs.presentation.core.extensions.collectAsEffect
import com.dcs.presentation.core.model.GenderUiState
import com.dcs.presentation.core.model.KnownForUiState
import com.dcs.presentation.core.model.MediaTypeUiState
import com.dcs.presentation.core.model.PersonCreditUiState
import com.dcs.presentation.core.model.PersonDetailUiState
import com.dcs.presentation.core.model.ProfileImageUiState
import com.dcs.presentation.core.theme.AllAboutMovieTheme
import com.dcs.presentation.core.theme.Gray1
import com.dcs.presentation.core.ui.state.UiState
import com.dcs.presentation.ui.persondetail.component.PersonProfileImage
import com.dcs.presentation.ui.persondetail.component.ProfileImageDetails

@Composable
fun PersonDetailRoute(
    navigateUp: () -> Unit,
    navigateToMovieDetail: (Int) -> Unit,
    navigateToTvShowDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PersonDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    viewModel.effect.collectAsEffect {
        when (it) {
            is PersonDetailEffect.NavigateToMovieDetail -> {
                navigateToMovieDetail(it.movieId)
            }

            is PersonDetailEffect.NavigateToTvShowDetail -> {
                navigateToTvShowDetail(it.tvShowId)
            }

            PersonDetailEffect.NavigateUp -> {
                navigateUp()
            }
        }
    }

    PersonDetailScreen(
        uiState = uiState,
        onPersonDetailUiEvent = viewModel::dispatchEvent,
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
private fun PersonDetailScreen(
    uiState: UiState<PersonDetailUiState>,
    onPersonDetailUiEvent: (PersonDetailUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    var isProfileImagesShowing by remember { mutableStateOf(false) }
    val onProfileImagesShowingChanged = remember {
        { isProfileImagesShowing = !isProfileImagesShowing }
    }

    BackHandler(enabled = isProfileImagesShowing) {
        onProfileImagesShowingChanged()
    }

    SharedTransitionLayout(modifier = modifier) {
        Box {
            LazyColumn {
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
                            NavigationBackButton(
                                onClick = { onPersonDetailUiEvent(PersonDetailUiEvent.OnNavigationBackButtonClick) }
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                when (uiState) {
                    is UiState.Loading -> {
                        item {
                            PersonDetailLoadingContent(
                                modifier = Modifier
                                    .fillParentMaxSize()
                                    .padding(30.dp)
                            )
                        }
                    }

                    is UiState.Error -> {
                        item {
                            ErrorScreen(
                                message = stringResource(id = R.string.api_response_error_message),
                                modifier = Modifier.fillParentMaxSize()
                            )
                        }
                    }

                    is UiState.Success -> {
                        val personDetailUiState = uiState.data
                        personDetailContent(
                            personDetailUiState = personDetailUiState,
                            onPersonDetailUiEvent = onPersonDetailUiEvent
                        ) {
                            PersonProfileImage(
                                uiState = personDetailUiState,
                                isProfileImagesShowing = isProfileImagesShowing,
                                onChanged = onProfileImagesShowingChanged,
                                modifier = Modifier.animateItem()
                            )
                        }
                    }
                }
            }
            if (uiState is UiState.Success) {
                val personDetailUiState = uiState.data
                ProfileImageDetails(
                    uiState = personDetailUiState,
                    isProfileImagesShowing = isProfileImagesShowing,
                    onChanged = onProfileImagesShowingChanged,
                )
            }
        }
    }
}

@Composable
private fun PersonDetailLoadingContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(Gray1)
        )
        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .width(150.dp)
                .height(40.dp)
                .background(Gray1)
        )

        Row(
            modifier = Modifier.padding(top = 12.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            )
            {
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(30.dp)
                        .background(Gray1)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(20.dp)
                        .background(Gray1)
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            )
            {
                Box(
                    modifier = Modifier
                        .width(150.dp)
                        .height(30.dp)
                        .background(Gray1)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(20.dp)
                        .background(Gray1)
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(top = 12.dp)
        )
        {
            Box(
                modifier = Modifier
                    .width(130.dp)
                    .height(30.dp)
                    .background(Gray1)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(20.dp)
                    .background(Gray1)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Box(
            modifier = Modifier
                .width(150.dp)
                .height(40.dp)
                .background(Gray1)
        )

        Box(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .height(200.dp)
                .background(Gray1)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PersonDetailScreenPreview(
    @PreviewParameter(PersonDetailProvider::class) uiState: UiState<PersonDetailUiState>,
) {
    AllAboutMovieTheme {
        PersonDetailScreen(
            uiState = uiState,
            onPersonDetailUiEvent = {}
        )
    }
}

private class PersonDetailProvider :
    CollectionPreviewParameterProvider<UiState<PersonDetailUiState>>(
        collection = listOf(
            UiState.Error(Exception("Error")),
            UiState.Loading,
            UiState.Success(
                PersonDetailUiState(
                    id = PersonId(1),
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
                    credits = mapOf(
                        "Acting" to listOf(
                            PersonCreditUiState(
                                id = MediaContentId(0),
                                adult = false,
                                mediaType = MediaTypeUiState.MOVIE,
                                posterPath = "/posterPath",
                                releaseDate = "2021-01-01",
                                title = "Title",
                                role = "Role",
                            )
                        ),
                        "Production" to listOf(
                            PersonCreditUiState(
                                id = MediaContentId(1),
                                adult = false,
                                mediaType = MediaTypeUiState.TV,
                                posterPath = "/posterPath",
                                releaseDate = "2021-01-01",
                                title = "Title",
                                role = "Role",
                            )
                        )
                    ),
                    knownFor = listOf(
                        KnownForUiState(
                            id = MediaContentId(0),
                            name = "good",
                            originalName = "or",
                            adult = false,
                            backdropPath = "b",
                            firstAirDate = "",
                            genreIds = listOf(),
                            mediaType = MediaTypeUiState.MOVIE,
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
                    creditCounts = 20,
                    profileImages = listOf(
                        ProfileImageUiState(
                            aspectRatio = 1.0,
                            filePath = "/profilePath",
                            height = 1000,
                            width = 1000,
                            voteCount = 0,
                            iso6391 = "en",
                            voteAverage = 0.0
                        )
                    ),
                )
            )
        )
    )
