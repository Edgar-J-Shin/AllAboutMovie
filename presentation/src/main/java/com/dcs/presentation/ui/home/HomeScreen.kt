package com.dcs.presentation.ui.home

import android.view.ViewTreeObserver
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.dcs.presentation.R
import com.dcs.presentation.core.common.ErrorScreen
import com.dcs.presentation.core.common.LoadingScreen
import com.dcs.presentation.core.model.MovieItemUiState
import com.dcs.presentation.core.state.UiState
import com.dcs.presentation.ui.trend.MovieItem
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    Scaffold(
        topBar = {
            SearchTopBar(
                viewModel.queryText,
                viewModel::onSearchTextChange,
                viewModel::onSearchTextClear
            )
        }
    ) { innerPadding ->
        Box(
            modifier = modifier.padding(innerPadding)
        ) {
            SearchUiStateScreen(
                modifier = Modifier
                    .fillMaxSize(),
                movies = viewModel.searchResult
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTopBar(
    queryText: String,
    onQueryChange: (String) -> Unit,
    onQueryClear: () -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    val view = LocalView.current
    val viewTreeObserver = view.viewTreeObserver
    DisposableEffect(viewTreeObserver) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            val isKeyboardOpen = ViewCompat.getRootWindowInsets(view)
                ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true

            if (!isKeyboardOpen) {
                focusManager.clearFocus()
            }
        }

        viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose {
            viewTreeObserver.removeOnGlobalLayoutListener(listener)
        }
    }

    SearchBar(
        query = queryText,
        onQueryChange = onQueryChange,
        onSearch = {
            onQueryChange(it)
            keyboardController?.hide()
            focusManager.clearFocus()
        },
        active = false,
        onActiveChange = { },
        placeholder = { Text(text = stringResource(id = R.string.searchbar_hint)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(id = R.string.desc_search)) },
        trailingIcon = {
            IconButton(onClick = onQueryClear) {
                Icon(imageVector = Icons.Default.Close, contentDescription = stringResource(id = R.string.desc_clear))
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusable()
    ) {
    }
}

@Composable
fun SearchUiStateScreen(
    modifier: Modifier = Modifier,
    movies: StateFlow<UiState<PagingData<MovieItemUiState>>>,
) {
    Box(
        modifier = modifier
    ) {
        val uiState by movies.collectAsStateWithLifecycle()

        when (uiState) {
            is UiState.Loading -> LoadingScreen()

            is UiState.Success -> GridMovieList(
                movieItems = (uiState as UiState.Success<PagingData<MovieItemUiState>>).data as PagingData<MovieItemUiState>
            )

            is UiState.Error -> ErrorScreen(
                message = stringResource(id = R.string.api_response_error_message)
            )
        }
    }
}

@Composable
fun GridMovieList(
    movieItems: PagingData<MovieItemUiState>,
    modifier: Modifier = Modifier,
) {
    val pagingItems = snapshotFlow { movieItems }.collectAsLazyPagingItems()
    val gridState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        state = gridState,
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
                        .fillMaxWidth(),
                    movie = movie,
                    onClick = { }
                )
            }
        }
    }
}

