package com.dcs.presentation.ui.home

import android.view.ViewTreeObserver
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dcs.presentation.R
import com.dcs.presentation.core.model.KeywordUiState
import com.dcs.presentation.core.model.MovieItemUiState
import com.dcs.presentation.ui.trend.MovieItem
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val searchHistory by viewModel.searchHistory.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            SearchTopBar(
                queryText = viewModel.queryText,
                searchHistory = searchHistory,
                onHomeUiEvent = viewModel::dispatchEvent
            )
        },
        modifier = modifier.fillMaxSize(),
    ) { innerPadding ->
        SearchScreen(
            movies = viewModel.searchResult,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTopBar(
    queryText: String,
    searchHistory: List<KeywordUiState>,
    onHomeUiEvent: (HomeUiEvent) -> Unit,
) {
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

    var active by remember { mutableStateOf(false) }

    SearchBar(
        query = queryText,
        onQueryChange = {
            onHomeUiEvent(HomeUiEvent.SearchTextChanged(it))
        },
        onSearch = {
            active = false
            onHomeUiEvent(HomeUiEvent.SearchText(it))
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text(text = stringResource(id = R.string.searchbar_hint)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(id = R.string.desc_search)) },
        trailingIcon = {
            if (active) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.desc_clear),
                    modifier = Modifier.clickable {
                        if (queryText.isNotEmpty()) {
                            onHomeUiEvent(HomeUiEvent.ClearSearchText)
                        } else {
                            active = false
                        }
                    }
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .focusable()
    ) {
        LazyColumn {
            items(
                count = searchHistory.count(),
                key = { index -> index }
            ) { index ->
                HistoryItem(
                    keyword = searchHistory[index].keyword,
                    onClick = {
                        active = false
                        onHomeUiEvent(HomeUiEvent.SearchText(it))
                    },
                    onClickRemove = { onHomeUiEvent(HomeUiEvent.DeleteHistory(it)) }
                )
            }
        }

        HorizontalDivider()
        Text(
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.clear_all_history),
            modifier = Modifier
                .padding(all = 12.dp)
                .fillMaxWidth()
                .clickable {
                    onHomeUiEvent(HomeUiEvent.DeleteAllHistory)
                }
        )
    }
}

@Composable
fun HistoryItem(
    keyword: String,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit = {},
    onClickRemove: (String) -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(all = 12.dp)
            .clickable { onClick(keyword) }
    ) {
        Icon(
            imageVector = Icons.Default.History,
            contentDescription = stringResource(id = R.string.desc_history)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = keyword)
        Spacer(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = stringResource(id = R.string.desc_clear),
            modifier = Modifier.clickable { onClickRemove(keyword) }
        )
    }
}

@Composable
fun SearchScreen(
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

