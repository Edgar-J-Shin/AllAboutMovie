package com.dcs.presentation.ui.moviedetail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.widget.ErrorScreen
import com.dcs.presentation.core.designsystem.widget.LoadingScreen
import com.dcs.presentation.core.extensions.collectAsEffect
import com.dcs.presentation.core.model.MovieDetailUiState
import com.dcs.presentation.core.model.MovieDetailUiStateProvider
import com.dcs.presentation.core.ui.state.UiState
import com.dcs.presentation.core.theme.AllAboutMovieTheme

@Composable
fun MovieDetailRoute(
    navigateUp: () -> Boolean,
    viewModel: MovieDetailViewModel = hiltViewModel(),
    showSnackBar: (String, SnackbarDuration) -> Unit = { _, _ -> },
) {
    val context = LocalContext.current

    viewModel.effect.collectAsEffect { effect ->
        when (effect) {
            is MovieDetailEffect.NavigateBack -> {
                navigateUp()
            }

            is MovieDetailEffect.ShowSnackbar -> {
                showSnackBar(
                    context.getString(effect.state.messageResId),
                    effect.state.duration
                )
            }
        }
    }

    val movie = viewModel.movie.collectAsStateWithLifecycle()

    MovieDetailScreen(
        uiState = movie.value,
        onMovieDetailEvent = viewModel::dispatchEvent,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun MovieDetailScreen(
    uiState: UiState<MovieDetailUiState>,
    onMovieDetailEvent: (MovieDetailUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier
    ) {
        item {
            MovieDetailTopAppBar(
                title = if (uiState is UiState.Success) {
                    uiState.data.title
                } else {
                    stringResource(id = R.string.route_movie_detail_name)
                },
                onBackClick = { onMovieDetailEvent(MovieDetailUiEvent.NavigateBack) }
            )
        }

        when (uiState) {
            is UiState.Loading -> {
                item {
                    LoadingScreen()
                }
            }

            is UiState.Error -> {
                item {
                    ErrorScreen(
                        message = stringResource(id = R.string.api_response_error_message),
                    )
                }
            }

            is UiState.Success -> {
                movieDetailContent(
                    movieDetailUiState = uiState.data,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MovieDetailTopAppBar(
    title: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(id = R.string.app_bar_back_content_description)
                )
            }
        },
        modifier = modifier.statusBarsPadding(),
    )
}

@Preview(showBackground = true)
@Composable
fun MovieDetailScreenPreview(
    @PreviewParameter(MovieDetailUiStateProvider::class) item: UiState<MovieDetailUiState>,
) {
    AllAboutMovieTheme {
        MovieDetailScreen(
            uiState = item,
            onMovieDetailEvent = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}



