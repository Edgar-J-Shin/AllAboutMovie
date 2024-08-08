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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.widget.ErrorScreen
import com.dcs.presentation.core.designsystem.widget.LoadingScreen
import com.dcs.presentation.core.model.MovieItemUiState
import com.dcs.presentation.ui.trend.MovieItem

@Composable
fun SearchResultRoute(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SearchResultViewModel = hiltViewModel(),
) {
    val pagingItems = viewModel.searchResult.collectAsLazyPagingItems()

    LaunchedEffect(key1 = SearchResultViewModel::class) {
        viewModel.effect.collect {
            when (it) {
                SearchResultEffect.NavigateBack -> {
                    navController.popBackStack()
                }
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
    pagingItems: LazyPagingItems<MovieItemUiState>,
    onSearchResultEvent: (SearchResultUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            SearchResultTopAppBar(onBackClick = { onSearchResultEvent(SearchResultUiEvent.NavigateBack) })
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
                isLoading -> LoadingScreen()

                isError -> ErrorScreen()

                isEmpty -> ErrorScreen(message = stringResource(id = R.string.empty_content_list_message))

                isNotLoading -> {
                    VerticalGridMovie(
                        movieItems = pagingItems
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
    movieItems: LazyPagingItems<MovieItemUiState>,
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
            count = movieItems.itemCount,
            key = { index -> index }
        ) { index ->
            movieItems[index]?.let { movie ->
                MovieItem(
                    modifier = Modifier
                        .fillMaxWidth(),
                    movie = movie,
                    onClick = { }
                )
            }
        }
    }
}

