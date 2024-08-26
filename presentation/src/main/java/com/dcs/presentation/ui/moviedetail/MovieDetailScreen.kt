package com.dcs.presentation.ui.moviedetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.widget.ErrorScreen
import com.dcs.presentation.core.designsystem.widget.LoadingScreen
import com.dcs.presentation.core.extensions.collectAsEffect
import com.dcs.presentation.core.model.MovieDetailUiState
import com.dcs.presentation.core.model.getBackdropPathUrl
import com.dcs.presentation.core.model.getPosterPathUrl
import com.dcs.presentation.core.model.getTitleOrNameWithReleaseYear
import com.dcs.presentation.core.state.UiState

@Composable
fun MovieDetailRoute(
    navController: NavHostController,
    viewModel: MovieDetailViewModel = hiltViewModel(),
    showSnackBar: (String, SnackbarDuration) -> Unit = { _, _ -> },
) {
    val context = LocalContext.current

    viewModel.effect.collectAsEffect { effect ->
        when (effect) {
            is MovieDetailEffect.NavigateBack -> {
                navController.popBackStack()
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
        movie = movie.value,
        onMovieDetailEvent = viewModel::dispatchEvent,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun MovieDetailScreen(
    movie: UiState<MovieDetailUiState>,
    onMovieDetailEvent: (MovieDetailUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            MovieDetailTopAppBar(onBackClick = { onMovieDetailEvent(MovieDetailUiEvent.NavigateBack) })
        },
        modifier = modifier,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (movie) {
                is UiState.Loading -> {
                    LoadingScreen()
                }

                is UiState.Error -> {
                    ErrorScreen(
                        message = stringResource(id = R.string.api_response_error_message),
                    )
                }

                is UiState.Success -> {
                    MovieDetailContents(
                        movie = movie.data,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailTopAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(stringResource(id = R.string.route_movie_detail_name))
        },
        modifier = modifier.statusBarsPadding(),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        },
    )
}

@Composable
fun MovieDetailContents(
    movie: MovieDetailUiState,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
    ) {
        MovieDetailImage(
            movie = movie,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f)

        )

        MovieDetailTitle(
            movie.getTitleOrNameWithReleaseYear(),
            modifier = Modifier
                .fillMaxWidth()
        )

        MovieDetailScore(
            movie.voteAverage,
            modifier = Modifier
                .fillMaxWidth()
        )

        MovieDetailInfo(
            movie.releaseDate,
            movie.originCountry[0],
            movie.runtime,
            movie.genres.joinToString(","),
            modifier = Modifier
                .fillMaxWidth()
        )

        MovieDetailTagline(
            movie.tagline,
            modifier = Modifier
                .fillMaxWidth()
        )

        MovieDetailOverview(
            movie.overview,
            modifier = Modifier
                .fillMaxWidth()
        )

        MovieDetailCrew(
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieDetailImage(
    movie: MovieDetailUiState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        GlideImage(
            model = movie.getBackdropPathUrl(),
            contentDescription = stringResource(id = R.string.movie_image_content_description),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop
        )

        GlideImage(
            model = movie.getPosterPathUrl(),
            contentDescription = stringResource(id = R.string.movie_image_content_description),
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(0.75f)
                .padding(all = 20.dp)
                .align(Alignment.CenterStart)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun MovieDetailTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}

@Composable
fun MovieDetailScore(
    scorePercentage: Double,
    modifier: Modifier = Modifier,
) {

}

@Composable
fun MovieDetailInfo(
    releaseDate: String,
    country: String,
    runtime: Int,
    genres: String,
    modifier: Modifier = Modifier,
) {

}

@Composable
fun MovieDetailTagline(
    tagline: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = tagline,
        color = Color.LightGray,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}

@Composable
fun MovieDetailOverview(
    overview: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(id = R.string.movie_detail_overview_title),
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )

    Text(
        text = overview,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier
    )
}

@Composable
fun MovieDetailCrew(
    modifier: Modifier = Modifier,
) {

}
