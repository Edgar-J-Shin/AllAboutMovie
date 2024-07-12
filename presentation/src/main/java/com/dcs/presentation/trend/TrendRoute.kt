package com.dcs.presentation.trend

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.dcs.presentation.R
import com.dcs.presentation.common.ErrorScreen
import com.dcs.presentation.common.LoadingScreen
import com.dcs.presentation.core.model.MovieItemUiState
import com.dcs.presentation.core.state.UiState
import kotlinx.coroutines.flow.StateFlow

@Composable
fun TrendRoute(
    modifier: Modifier = Modifier,
    viewModel: TrendViewModel = hiltViewModel()
) {
    Scaffold { innerPadding ->
        Box(
            modifier = modifier.padding(innerPadding)
        ) {
            val uiState by viewModel.moviesByTrending.collectAsStateWithLifecycle()

            when (uiState) {
                is UiState.Loading -> LoadingScreen()

                is UiState.Success -> TrendMovieList(
                    movieItems = (uiState as UiState.Success<StateFlow<PagingData<MovieItemUiState>>>).data as StateFlow<PagingData<MovieItemUiState>>
                )

                is UiState.Failure,
                is UiState.Error -> ErrorScreen(
                    message = stringResource(id = R.string.api_response_error_message)
                )
            }
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
            key = pagingItems.itemKey { it.id }
        ) { index ->
            pagingItems[index]?.let { movie ->
                MovieItem(
                    modifier = Modifier
                        .width(160.dp),
                    movie = movie,
                    onClick = { }
                )
            }
        }
    }
}