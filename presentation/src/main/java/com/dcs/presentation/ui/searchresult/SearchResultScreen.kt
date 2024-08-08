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
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dcs.presentation.R
import com.dcs.presentation.core.model.MovieItemUiState
import com.dcs.presentation.ui.trend.MovieItem
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SearchResultRoute(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SearchResultViewModel = hiltViewModel(),
) {
    LaunchedEffect(key1 = SearchResultViewModel::class) {
        viewModel.effect.collect {
            when (it) {
                SearchResultEffect.NavigateBack -> {
                    navController.popBackStack()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            SearchResultTopAppBar(onBackClick = { viewModel.dispatchEvent(SearchResultUiEvent.NavigateBack) })
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        SearchResultScreen(
            movies = viewModel.searchResult,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchResultTopAppBar(
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
fun SearchResultScreen(
    movies: StateFlow<PagingData<MovieItemUiState>>,
    modifier: Modifier = Modifier,
) {
    val pagingItems = movies.collectAsLazyPagingItems()

    Box(
        modifier = modifier
    ) {
        VerticalGridMovie(
            movieItems = pagingItems
        )
    }
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

