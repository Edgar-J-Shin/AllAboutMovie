package com.dcs.presentation.ui.moviedetail

import android.icu.text.NumberFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.widget.CircularProgressBarWithPercentage
import com.dcs.presentation.core.designsystem.widget.ErrorScreen
import com.dcs.presentation.core.designsystem.widget.ImageLoader
import com.dcs.presentation.core.designsystem.widget.ImageLoaderData
import com.dcs.presentation.core.designsystem.widget.LoadingScreen
import com.dcs.presentation.core.extensions.collectAsEffect
import com.dcs.presentation.core.model.CastUiState
import com.dcs.presentation.core.model.MovieDetailUiState
import com.dcs.presentation.core.model.getBackdropPathUrl
import com.dcs.presentation.core.model.getCrew
import com.dcs.presentation.core.model.getGenres
import com.dcs.presentation.core.model.getPosterPathUrl
import com.dcs.presentation.core.model.getProfilePathUrl
import com.dcs.presentation.core.model.getRuntime
import com.dcs.presentation.core.model.getSpokenLanguage
import com.dcs.presentation.core.model.getTitleWithReleaseYear
import com.dcs.presentation.core.model.getVotePercentage
import com.dcs.presentation.core.state.UiState
import java.util.Locale

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
    val innerModifier = Modifier
        .padding(horizontal = 12.dp)

    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier
    ) {
        item {
            MovieDetailImage(
                movie = movie,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f)
                    .padding(bottom = 6.dp)

            )
        }
        item {
            MovieDetailTitle(
                movie.getTitleWithReleaseYear(),
                modifier = innerModifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            )
        }
        item {
            MovieDetailScore(
                movie.getVotePercentage(),
                modifier = innerModifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            )
        }
        item {
            MovieDetailInfo(
                releaseDate = movie.releaseDate,
                country = movie.originCountry[0],
                runtime = movie.getRuntime(),
                genres = movie.getGenres(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            )
        }
        item {
            MovieDetailTagline(
                movie.tagline,
                modifier = innerModifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            )
        }
        item {
            MovieDetailOverview(
                movie.overview,
                modifier = innerModifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            )
        }
        item {
            MovieDetailCrew(
                movie.getCrew(),
                modifier = innerModifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            )
        }
        item {
            MovieDetailCastSector(
                casts = movie.credits.cast,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            )
        }
        item {
            MovieDetailInfoFooter(
                language = movie.getSpokenLanguage(),
                budget = movie.budget,
                revenue = movie.revenue,
                modifier = innerModifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
            )
        }
        item {
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun MovieDetailImage(
    movie: MovieDetailUiState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        ImageLoader(
            imageLoaderData = ImageLoaderData(
                imageUrl = movie.getBackdropPathUrl(),
                contentDescResId = R.string.movie_image_content_description
            ),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        )

        ImageLoader(
            imageLoaderData = ImageLoaderData(
                imageUrl = movie.getPosterPathUrl(),
                loadingResId = null,
                failureResId = null,
                contentDescResId = R.string.movie_image_content_description
            ),
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(0.75f)
                .padding(all = 20.dp)
                .align(Alignment.CenterStart)
                .clip(RoundedCornerShape(8.dp)),
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
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}

@Composable
fun MovieDetailScore(
    scorePercentage: Int,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        CircularProgressBarWithPercentage(
            modifier = Modifier,
            percentage = scorePercentage,
            viewSize = 40.dp
        )

        Text(
            text = stringResource(id = R.string.user_score),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp),
        )
    }
}

@Composable
fun MovieDetailInfo(
    releaseDate: String,
    country: String,
    runtime: String,
    genres: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "$releaseDate ($country) . $runtime",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = genres,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )
    }
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
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.movie_detail_overview_title),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
        )

        Text(
            text = overview,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(top = 8.dp)
        )
    }
}

@Composable
fun MovieDetailCrew(
    crews: List<Pair<String, String>>,
    modifier: Modifier = Modifier,
) {
    (0..1).forEach { rowNum ->
        Row(
            modifier = modifier
        ) {
            (0..1).forEach { colNum ->
                val index = rowNum * 2 + colNum
                if (crews.size <= index) return@Row

                MovieDescText(
                    title = crews[index].first,
                    description = crews[index].second,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }
    }
}

@Composable
fun MovieDetailCastSector(
    casts: List<CastUiState>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.top_billed_cast),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        )

        if (casts.isEmpty()) {
            ErrorScreen(
                message = stringResource(id = R.string.top_billed_cast_empty_message),
                textStyle = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(
                    vertical = 12.dp
                )
            )
        } else {
            LazyRow(
                contentPadding = PaddingValues(
                    horizontal = 6.dp,
                    vertical = 12.dp
                ),
                state = rememberLazyListState(),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(
                    count = casts.count()
                ) { index ->
                    MovieDetailCast(
                        cast = casts[index],
                        modifier = Modifier
                            .width(140.dp)
                            .aspectRatio(0.6f)
                            .padding(horizontal = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun MovieDetailCast(
    cast: CastUiState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() }
    ) {
        ImageLoader(
            imageLoaderData = ImageLoaderData(
                imageUrl = cast.getProfilePathUrl(),
                contentDescResId = R.string.cast_image_content_description
            ),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.9f)
        )

        Text(
            text = cast.name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 16.dp
                )
        )

        Text(
            text = cast.character,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 4.dp
                )
        )
    }
}

@Composable
fun MovieDetailInfoFooter(
    language: String,
    budget: Int,
    revenue: Int,
    modifier: Modifier = Modifier,
    format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("en-US")),
) {
    if (language.isNotEmpty()) {
        MovieDescText(
            title = stringResource(id = R.string.movie_detail_original_language_title),
            description = language,
            modifier = modifier
        )
    }
    if (budget > 0) {
        MovieDescText(
            title = stringResource(id = R.string.movie_detail_budget_title),
            description = format.format(budget),
            modifier = modifier
        )
    }
    if (revenue > 0) {
        MovieDescText(
            title = stringResource(id = R.string.movie_detail_revenue_title),
            description = format.format(revenue),
            modifier = modifier
        )
    }
}

@Composable
fun MovieDescText(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(top = 4.dp)
        )
    }
}

