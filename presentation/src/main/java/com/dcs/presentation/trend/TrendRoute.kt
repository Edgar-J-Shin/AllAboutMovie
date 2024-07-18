package com.dcs.presentation.trend

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
import com.dcs.presentation.common.ErrorScreen
import com.dcs.presentation.common.LoadingScreen
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
            TrendMoviePanel(
                title = stringResource(id = R.string.movie_trend_title_trend),
                tabs = MovieTrendType.entries.map { it.toUiString() },
                movies = viewModel.moviesByTrending
            ) { tabIndex ->
                viewModel.updateMovieTrendType(MovieTrendType.entries[tabIndex])
            }

            TrendMoviePanel(
                title = stringResource(id = R.string.movie_trend_title_popular),
                tabs = MoviePopularType.entries.map { it.toUiString() },
                movies = viewModel.moviesByPopular
            ) { tabIndex ->
                viewModel.updateMoviePopularType(MoviePopularType.entries[tabIndex])
            }

            TrendMoviePanel(
                title = stringResource(id = R.string.movie_trend_title_upcoming),
                movies = viewModel.moviesByUpcoming
            )
        }
    }
}

@Composable
fun TrendMoviePanel(
    modifier: Modifier = Modifier,
    title: String,
    tabs: List<String> = listOf(),
    movies: StateFlow<UiState<StateFlow<PagingData<MovieItemUiState>>>>,
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

        TrendMovieUiStateScreen(movies)

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
    movies: StateFlow<UiState<StateFlow<PagingData<MovieItemUiState>>>>,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
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