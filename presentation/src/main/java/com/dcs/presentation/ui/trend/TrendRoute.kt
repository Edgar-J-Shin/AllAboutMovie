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
import com.dcs.domain.model.MediaType
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.component.ErrorScreen
import com.dcs.presentation.core.designsystem.component.LoadingScreen
import com.dcs.presentation.core.extensions.collectAsEffect
import com.dcs.presentation.core.model.MediaContentUiState
import com.dcs.presentation.core.model.MediaContentUiStateProvider
import com.dcs.presentation.core.model.PopularMovieUiType
import com.dcs.presentation.core.model.TrendingMovieUiType
import com.dcs.presentation.core.model.toMediaContentId
import com.dcs.presentation.core.theme.AllAboutMovieTheme
import com.dcs.presentation.ui.trend.component.MediaItem
import kotlinx.coroutines.flow.flowOf

@Composable
fun TrendRoute(
    navigateToMovieDetails: (Int) -> Unit,
    navigateToTvShowDetails: (Int) -> Unit,
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

            is TrendEffect.NavigateToMovieDetails -> {
                navigateToMovieDetails(effect.mediaContentId.value)
            }

            is TrendEffect.NavigateToTvShowDetails -> {
                navigateToTvShowDetails(effect.mediaContentId.value)
            }
        }
    }

    val scrollState = rememberScrollState()
    val trendingMovies = viewModel.trendingMovies.collectAsLazyPagingItems()
    val popularMovies = viewModel.popularMovies.collectAsLazyPagingItems()
    val upcomingMovies = viewModel.moviesByUpcoming.collectAsLazyPagingItems()

    TrendScreen(
        trendingMovies = trendingMovies,
        popularMovies = popularMovies,
        upcomingMovies = upcomingMovies,
        onTrendingMovieUiTypeChange = viewModel::updateTrendingMovieUiType,
        onPopularMovieUiTypeChange = viewModel::updatePopularMovieUiType,
        onTrendUiEvent = viewModel::dispatchEvent,
        scrollState = scrollState,
        modifier = modifier
    )
}

@Composable
fun TrendScreen(
    trendingMovies: LazyPagingItems<MediaContentUiState>,
    popularMovies: LazyPagingItems<MediaContentUiState>,
    upcomingMovies: LazyPagingItems<MediaContentUiState>,
    modifier: Modifier = Modifier,
    onTrendingMovieUiTypeChange: (TrendingMovieUiType) -> Unit = {},
    onPopularMovieUiTypeChange: (PopularMovieUiType) -> Unit = {},
    onTrendUiEvent: (TrendUiEvent) -> Unit = {},
    scrollState: ScrollState = rememberScrollState(),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        TrendingMovieSector(
            pagingItems = trendingMovies,
            onTabClick = { tabIndex -> onTrendingMovieUiTypeChange(TrendingMovieUiType.entries[tabIndex]) },
            onTrendUiEvent = onTrendUiEvent,
        )

        PopularMovieSector(
            pagingItems = popularMovies,
            onTabClick = { tabIndex -> onPopularMovieUiTypeChange(PopularMovieUiType.entries[tabIndex]) },
            onTrendUiEvent = onTrendUiEvent,
        )

        UpcomingMovieSector(
            pagingItems = upcomingMovies,
            onTrendUiEvent = onTrendUiEvent,
        )
    }
}

@Composable
fun TrendingMovieSector(
    pagingItems: LazyPagingItems<MediaContentUiState>,
    onTabClick: (Int) -> Unit = {},
    onTrendUiEvent: (TrendUiEvent) -> Unit = {},
) {
    MediaContentSector(
        title = stringResource(id = R.string.movie_trend_title_trend),
        pagingItems = pagingItems,
        tabs = TrendingMovieUiType.entries.map { it.toUiString() },
        onTabClick = onTabClick,
        onTrendUiEvent = onTrendUiEvent
    )
}

@Composable
fun PopularMovieSector(
    pagingItems: LazyPagingItems<MediaContentUiState>,
    onTabClick: (Int) -> Unit = {},
    onTrendUiEvent: (TrendUiEvent) -> Unit = {},
) {
    MediaContentSector(
        title = stringResource(id = R.string.movie_trend_title_popular),
        pagingItems = pagingItems,
        tabs = PopularMovieUiType.entries.map { it.toUiString() },
        onTabClick = onTabClick,
        onTrendUiEvent = onTrendUiEvent
    )
}

@Composable
fun UpcomingMovieSector(
    pagingItems: LazyPagingItems<MediaContentUiState>,
    onTrendUiEvent: (TrendUiEvent) -> Unit = {},
) {
    MediaContentSector(
        title = stringResource(id = R.string.movie_trend_title_upcoming),
        pagingItems = pagingItems,
        onTrendUiEvent = onTrendUiEvent
    )
}

@Composable
fun MediaContentSector(
    title: String,
    pagingItems: LazyPagingItems<MediaContentUiState>,
    modifier: Modifier = Modifier,
    tabs: List<String> = listOf(),
    onTabClick: (Int) -> Unit = {},
    onTrendUiEvent: (TrendUiEvent) -> Unit = {},
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
                onTabClick(tabIndex)
            }
        }

        MediaContents(
            pagingItems = pagingItems,
            onTrendUiEvent = onTrendUiEvent,
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
fun MediaContents(
    pagingItems: LazyPagingItems<MediaContentUiState>,
    onTrendUiEvent: (TrendUiEvent) -> Unit = {},
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
                MediaItems(
                    pagingItems = pagingItems,
                    onTrendUiEvent = onTrendUiEvent
                )
            }
        }
    }
}

@Composable
fun MediaItems(
    pagingItems: LazyPagingItems<MediaContentUiState>,
    modifier: Modifier = Modifier,
    onTrendUiEvent: (TrendUiEvent) -> Unit = {},
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
            pagingItems[index]?.let { mediaItem ->

                MediaItem(
                    mediaContentUiState = mediaItem,
                    onClick = {
                        onTrendUiEvent(
                            TrendUiEvent.OnMediaItemClick(
                                mediaContentId = mediaItem.toMediaContentId(),
                                mediaType = MediaType.toMediaType(mediaItem.mediaType)
                            )
                        )
                    },
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
    @PreviewParameter(MediaContentUiStateProvider::class) items: PagingData<MediaContentUiState>,
) {
    val pagingItems1 = flowOf(items).collectAsLazyPagingItems()
    val pagingItems2 = flowOf(items).collectAsLazyPagingItems()
    val pagingItems3 = flowOf(items).collectAsLazyPagingItems()

    AllAboutMovieTheme {
        TrendScreen(
            trendingMovies = pagingItems1,
            popularMovies = pagingItems2,
            upcomingMovies = pagingItems3,
            modifier = Modifier.fillMaxSize(),
        )
    }
}



