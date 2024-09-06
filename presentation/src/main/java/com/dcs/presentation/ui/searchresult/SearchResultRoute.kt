package com.dcs.presentation.ui.searchresult

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dcs.domain.model.MediaContentId
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.component.ErrorScreen
import com.dcs.presentation.core.designsystem.component.LoadingScreen
import com.dcs.presentation.core.extensions.collectAsEffect
import com.dcs.presentation.core.model.MediaContentUiState
import com.dcs.presentation.core.model.MediaContentUiStateProvider
import com.dcs.presentation.core.model.toMediaContentId
import com.dcs.presentation.core.theme.AllAboutMovieTheme
import com.dcs.presentation.ui.trend.component.MediaItem
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchResultRoute(
    navigateUp: () -> Boolean,
    navigateToDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchResultViewModel = hiltViewModel(),
) {
    val pagingItems = viewModel.searchResult.collectAsLazyPagingItems()

    viewModel.effect.collectAsEffect { effect ->
        when (effect) {
            SearchResultEffect.NavigateBack -> {
                navigateUp()
            }

            is SearchResultEffect.NavigateToMovieDetails -> {
                navigateToDetails(effect.mediaContentId.value)
            }
        }
    }

    SearchResultScreen(
        pagingItems = pagingItems,
        onSearchResultEvent = viewModel::dispatchEvent,
        modifier = modifier.fillMaxSize()
    )
}

@Composable
private fun SearchResultScreen(
    pagingItems: LazyPagingItems<MediaContentUiState>,
    onSearchResultEvent: (SearchResultUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            SearchResultTopAppBar(onBackClick = { onSearchResultEvent(SearchResultUiEvent.OnNavigationBackClick) })
        },
        modifier = modifier,
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val isLoading = pagingItems.loadState.refresh is LoadState.Loading
            val isNotLoading = pagingItems.loadState.refresh is LoadState.NotLoading
            val isError = pagingItems.loadState.refresh is LoadState.Error
            val isEmpty = pagingItems.itemCount == 0

            when {
                isLoading -> {
                    LoadingScreen()
                }

                isError -> {
                    ErrorScreen(
                        message = stringResource(id = R.string.api_response_error_message),
                        primaryButton = {
                            Button(
                                onClick = pagingItems::retry
                            ) {
                                Text(text = stringResource(id = R.string.retry))
                            }
                        },
                    )
                }

                isEmpty -> {
                    ErrorScreen(message = stringResource(id = R.string.empty_content_list_message))
                }

                isNotLoading -> {
                    VerticalGridMovie(
                        pagingItems = pagingItems,
                        onItemClick = { movieId ->
                            onSearchResultEvent(
                                SearchResultUiEvent.OnMediaItemClick(
                                    mediaContentId = movieId
                                )
                            )
                        },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultTopAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(stringResource(id = R.string.route_search_result_name))
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
fun VerticalGridMovie(
    pagingItems: LazyPagingItems<MediaContentUiState>,
    onItemClick: (MediaContentId) -> Unit,
    modifier: Modifier = Modifier,
) {
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        state = gridState,
        contentPadding = PaddingValues(
            horizontal = dimensionResource(id = R.dimen.list_margin_horizontal),
            vertical = dimensionResource(id = R.dimen.list_margin_vertical)
        ),
        modifier = modifier
    ) {
        items(
            count = pagingItems.itemCount,
            key = { index -> index }
        ) { index ->
            pagingItems[index]?.let { mediaItem ->
                MediaItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    mediaContentUiState = mediaItem,
                    onClick = {
                        onItemClick(mediaItem.toMediaContentId())
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchResultScreenPreview(
    @PreviewParameter(MediaContentUiStateProvider::class) items: PagingData<MediaContentUiState>,
) {
    AllAboutMovieTheme {
        val pagingItems = flowOf(items).collectAsLazyPagingItems()

        SearchResultScreen(
            pagingItems = pagingItems,
            onSearchResultEvent = { },
            modifier = Modifier.fillMaxSize()
        )
    }
}

