package com.dcs.presentation.ui.trend

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.dcs.presentation.R
import com.dcs.presentation.core.common.ErrorScreen
import com.dcs.presentation.core.common.LoadingScreen
import com.dcs.presentation.core.model.MovieItemUiState
import com.dcs.presentation.core.state.MoviePopularType
import com.dcs.presentation.core.state.MovieTrendType
import com.dcs.presentation.core.state.UiState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TrendRoute(
    modifier: Modifier = Modifier,
    viewModel: TrendViewModel = hiltViewModel()
) {

    Scaffold { innerPadding ->
        val scrollState = rememberScrollState()

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {
            TrendMovies(
                movies = viewModel.moviesByTrending,
                onTabClick = { tabIndex ->
                    viewModel.updateMovieTrendType(MovieTrendType.entries[tabIndex])
                }
            )

            PopularContents(
                movies = viewModel.moviesByPopular,
                onTabClick = { tabIndex ->
                    viewModel.updateMoviePopularType(MoviePopularType.entries[tabIndex])
                }
            )

            UpcomingMovies(
                movies = viewModel.moviesByUpcoming
            )
        }
    }
}

@Composable
fun TrendMovies(
    movies: StateFlow<UiState<StateFlow<PagingData<MovieItemUiState>>>>,
    onTabClick: (Int) -> Unit = {}
) {
    TrendMoviePanel(
        title = stringResource(id = R.string.movie_trend_title_trend),
        movies = movies,
        tabs = MovieTrendType.entries.map { it.toUiString() },
        onTabClick = onTabClick
    )
}

@Composable
fun PopularContents(
    movies: StateFlow<UiState<StateFlow<PagingData<MovieItemUiState>>>>,
    onTabClick: (Int) -> Unit = {}
) {
    TrendMoviePanel(
        title = stringResource(id = R.string.movie_trend_title_popular),
        movies = movies,
        tabs = MoviePopularType.entries.map { it.toUiString() },
        onTabClick = onTabClick
    )
}

@Composable
fun UpcomingMovies(
    movies: StateFlow<UiState<StateFlow<PagingData<MovieItemUiState>>>>
) {
    TrendMoviePanel(
        title = stringResource(id = R.string.movie_trend_title_upcoming),
        movies = movies
    )
}

@Composable
fun TrendMoviePanel(
    title: String,
    movies: StateFlow<UiState<StateFlow<PagingData<MovieItemUiState>>>>,
    modifier: Modifier = Modifier,
    tabs: List<String> = listOf(),
    onTabClick: (Int) -> Unit = {}
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

        TrendMovieUiStateScreen(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            movies = movies
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
    onTabClick: (Int) -> Unit
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
fun TrendMovieUiStateScreen(
    modifier: Modifier = Modifier,
    movies: StateFlow<UiState<StateFlow<PagingData<MovieItemUiState>>>>,
) {
    Box(
        modifier = modifier
    ) {
        val uiState by movies.collectAsStateWithLifecycle()

        when (uiState) {
            is UiState.Loading -> LoadingScreen()

            is UiState.Success -> TrendMovieList(
                movieItems = (uiState as UiState.Success<StateFlow<PagingData<MovieItemUiState>>>).data as StateFlow<PagingData<MovieItemUiState>>
            )

            is UiState.Error -> ErrorScreen(
                message = stringResource(id = R.string.api_response_error_message)
            )
        }
    }
}

@Composable
fun TrendMovieList(
    movieItems: StateFlow<PagingData<MovieItemUiState>>,
    modifier: Modifier = Modifier
) {
    val pagingItems = movieItems.collectAsLazyPagingItems()
    val listState = rememberLazyListState()

    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        state = listState,
        contentPadding = PaddingValues(
            horizontal = dimensionResource(id = R.dimen.list_margin_horizontal),
            vertical = dimensionResource(id = R.dimen.list_margin_vertical)
        )
    ) {
        items(
            count = pagingItems.itemCount,
            key = { index -> index }
        ) { index ->
            pagingItems[index]?.let { movie ->
                MovieItem(
                    modifier = Modifier
                        .width(dimensionResource(id = R.dimen.trend_item_width)),
                    movie = movie,
                    onClick = { }
                )
            }
        }
    }
}
