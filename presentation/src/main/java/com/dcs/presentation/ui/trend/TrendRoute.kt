package com.dcs.presentation.ui.trend

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.widget.ErrorScreen
import com.dcs.presentation.core.designsystem.widget.LoadingScreen
import com.dcs.presentation.core.extensions.collectAsEffect
import com.dcs.presentation.core.model.MovieItemUiState
import com.dcs.presentation.core.model.MovieItemUiStateProvider
import com.dcs.presentation.core.state.MoviePopularUiType
import com.dcs.presentation.core.state.MovieTrendUiType
import com.dcs.presentation.core.theme.AllAboutMovieTheme
import kotlinx.coroutines.flow.flowOf

@Composable
fun TrendRoute(
    modifier: Modifier = Modifier,
    viewModel: TrendViewModel = hiltViewModel(),
    showSnackBar: (String, SnackbarDuration) -> Unit = { _, _ -> },
) {

    val context = LocalContext.current

    viewModel.effect.collectAsEffect { effect ->
        when (effect) {

            is TrendEffect.ShowSnackbar -> {
                showSnackBar(
                    context.getString(effect.state.messageResId),
                    effect.state.duration
                )
            }
        }
    }

    val scrollState = rememberScrollState()
    val trendingMovies = viewModel.moviesByTrending.collectAsLazyPagingItems()
    val popularMovies = viewModel.moviesByPopular.collectAsLazyPagingItems()
    val upcomingMovies = viewModel.moviesByUpcoming.collectAsLazyPagingItems()

    TrendScreen(
        trendingMovies = trendingMovies,
        popularMovies = popularMovies,
        upcomingMovies = upcomingMovies,
        onMovieTrendTypeChange = { type ->
            viewModel.updateMovieTrendType(type)
        },
        onMoviePopularTypeChange = { type ->
            viewModel.updateMoviePopularType(type)
        },
        scrollState = scrollState,
        modifier = modifier
    )
}

@Composable
fun TrendScreen(
    trendingMovies: LazyPagingItems<MovieItemUiState>,
    popularMovies: LazyPagingItems<MovieItemUiState>,
    upcomingMovies: LazyPagingItems<MovieItemUiState>,
    modifier: Modifier = Modifier,
    onMovieTrendTypeChange: (MovieTrendUiType) -> Unit = {},
    onMoviePopularTypeChange: (MoviePopularUiType) -> Unit = {},
    scrollState: ScrollState = rememberScrollState(),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {

        TrendMovieSector(
            pagingItems = trendingMovies,
            onTabClick = { tabIndex -> onMovieTrendTypeChange(MovieTrendUiType.entries[tabIndex]) }
        )

        PopularMovieSector(
            pagingItems = popularMovies,
            onTabClick = { tabIndex -> onMoviePopularTypeChange(MoviePopularUiType.entries[tabIndex]) }
        )

        UpcomingMovieSector(
            pagingItems = upcomingMovies
        )
    }
}

@Composable
fun TrendMovieSector(
    pagingItems: LazyPagingItems<MovieItemUiState>,
    onTabClick: (Int) -> Unit = {},
) {
    MovieSector(
        title = stringResource(id = R.string.movie_trend_title_trend),
        pagingItems = pagingItems,
        tabs = MovieTrendUiType.entries.map { it.toUiString() },
        onTabClick = onTabClick
    )
}

@Composable
fun PopularMovieSector(
    pagingItems: LazyPagingItems<MovieItemUiState>,
    onTabClick: (Int) -> Unit = {},
) {
    MovieSector(
        title = stringResource(id = R.string.movie_trend_title_popular),
        pagingItems = pagingItems,
        tabs = MoviePopularUiType.entries.map { it.toUiString() },
        onTabClick = onTabClick
    )
}

@Composable
fun UpcomingMovieSector(
    pagingItems: LazyPagingItems<MovieItemUiState>,
) {
    MovieSector(
        title = stringResource(id = R.string.movie_trend_title_upcoming),
        pagingItems = pagingItems
    )
}

@Composable
fun MovieSector(
    title: String,
    pagingItems: LazyPagingItems<MovieItemUiState>,
    modifier: Modifier = Modifier,
    tabs: List<String> = listOf(),
    onTabClick: (Int) -> Unit = {},
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(all = 12.dp),
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = Color.Black
        )

        if (tabs.isNotEmpty()) {
            CustomScrollableTabRow(
                tabs = tabs,
                selectedTabIndex = selectedTabIndex,
            ) { tabIndex ->
                selectedTabIndex = tabIndex
                onTabClick.invoke(tabIndex)
            }
        }

        MovieContents(
            pagingItems = pagingItems,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
        )

        HorizontalDivider(
            Modifier.padding(all = 4.dp),
            color = Color.LightGray
        )
    }
}

@Composable
fun CustomScrollableTabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabClick: (Int) -> Unit,
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        contentColor = Color.Black,
        edgePadding = 0.dp,
        divider = {},
        modifier = Modifier
            .wrapContentSize(),
    ) {
        tabs.forEachIndexed { tabIndex, tab ->
            val selected = selectedTabIndex == tabIndex
            val textColor = if (selected) Color.DarkGray else Color.LightGray
            Tab(
                selected = selected,
                onClick = { onTabClick(tabIndex) },
                text = {
                    Text(
                        text = tab,
                        color = textColor
                    )
                }
            )
        }
    }
}

@Composable
fun MovieContents(
    pagingItems: LazyPagingItems<MovieItemUiState>,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        val isLoading = pagingItems.loadState.refresh is LoadState.Loading
        val isError = pagingItems.loadState.refresh is LoadState.Error
        val isEmpty = pagingItems.itemCount == 0

        when {
            isLoading -> {
                LoadingScreen()
            }

            isError -> {
                ErrorScreen(
                    message = stringResource(id = R.string.api_response_error_message)
                )
            }

            isEmpty -> {
                ErrorScreen(message = stringResource(id = R.string.empty_content_list_message))
            }

            else -> {
                MovieItems(
                    pagingItems = pagingItems
                )
            }
        }
    }
}

@Composable
fun MovieItems(
    pagingItems: LazyPagingItems<MovieItemUiState>,
    modifier: Modifier = Modifier,
    listState: LazyListState = rememberLazyListState(),
) {

    LazyRow(
        state = listState,
        contentPadding = PaddingValues(
            horizontal = dimensionResource(id = R.dimen.list_margin_horizontal),
            vertical = dimensionResource(id = R.dimen.list_margin_vertical)
        ),
        modifier = modifier
            .fillMaxWidth(),
    ) {
        items(
            count = pagingItems.itemCount,
            key = { index -> index }
        ) { index ->
            pagingItems[index]?.let { movie ->
                MovieItem(
                    movie = movie,
                    onClick = { },
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.trend_item_width))
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrendScreenPreview(
    @PreviewParameter(MovieItemUiStateProvider::class) items: PagingData<MovieItemUiState>,
) {
    val pagingItems1 = flowOf(items).collectAsLazyPagingItems()
    val pagingItems2 = flowOf(items).collectAsLazyPagingItems()
    val pagingItems3 = flowOf(items).collectAsLazyPagingItems()

    AllAboutMovieTheme {
        TrendScreen(
            trendingMovies = pagingItems1,
            popularMovies = pagingItems2,
            upcomingMovies = pagingItems3,
            modifier = Modifier.fillMaxSize()
        )
    }
}



