package com.dcs.presentation.ui.home

import android.view.ViewTreeObserver
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dcs.presentation.R
import com.dcs.presentation.core.extensions.collectAsEffect
import com.dcs.presentation.core.model.SearchUiState
import com.dcs.presentation.core.model.SearchUiStateProvider
import com.dcs.presentation.core.theme.AllAboutMovieTheme

@Composable
fun HomeRoute(
    searchActive: Boolean,
    navigateToSearchResult: (String) -> Unit,
    onSearchActiveChange: (Boolean) -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    showSnackBar: (String, SnackbarDuration) -> Unit = { _, _ -> },
) {
    val context = LocalContext.current

    viewModel.effect.collectAsEffect { effect ->
        when (effect) {
            is HomeEffect.NavigateToSearchResult -> {
                navigateToSearchResult(effect.keyword)
            }

            is HomeEffect.ShowSnackbar -> {
                showSnackBar(
                    context.getString(effect.state.messageResId),
                    effect.state.duration
                )
            }
        }
    }

    val searchUiState by viewModel.searchUiState.collectAsStateWithLifecycle()

    HomeScreen(
        searchUiState = searchUiState,
        searchActive = searchActive,
        onSearchActiveChange = onSearchActiveChange,
        onHomeUiEvent = viewModel::dispatchEvent,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun HomeScreen(
    searchUiState: SearchUiState,
    searchActive: Boolean,
    onSearchActiveChange: (Boolean) -> Unit,
    onHomeUiEvent: (HomeUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        SearchTopBar(
            searchUiState = searchUiState,
            searchActive = searchActive,
            onSearchActiveChange = onSearchActiveChange,
            onHomeUiEvent = onHomeUiEvent,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTopBar(
    searchUiState: SearchUiState,
    searchActive: Boolean,
    onSearchActiveChange: (Boolean) -> Unit,
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

    SearchBar(
        query = searchUiState.query.keyword,
        onQueryChange = {
            onHomeUiEvent(HomeUiEvent.OnSearchTextChanged(it))
        },
        onSearch = {
            onSearchActiveChange(false)
            onHomeUiEvent(HomeUiEvent.OnSearch(it))
        },
        active = searchActive,
        onActiveChange = { onSearchActiveChange(it) },
        placeholder = {
            Text(text = stringResource(id = R.string.searchbar_hint))
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.desc_search)
            )
        },
        trailingIcon = {
            if (searchActive) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.desc_clear),
                    modifier = Modifier.clickable {
                        if (searchUiState.queryNotEmpty()) {
                            onHomeUiEvent(HomeUiEvent.OnClearSearchTextClick)
                        } else {
                            onSearchActiveChange(false)
                        }
                    }
                )
            }
        },
        windowInsets = WindowInsets(
            top = 0.dp,
            bottom = 0.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .focusable()
    ) {
        LazyColumn {
            items(
                count = searchUiState.searchKeywords.count(),
                key = { index -> index }
            ) { index ->
                SearchKeyword(
                    keyword = searchUiState.searchKeywords[index].keyword,
                    onClick = {
                        onSearchActiveChange(false)
                        onHomeUiEvent(HomeUiEvent.OnSearchTextChanged(it))
                        onHomeUiEvent(HomeUiEvent.OnSearch(it))
                    },
                    onClickRemove = { onHomeUiEvent(HomeUiEvent.OnDeleteHistoryClick(it)) }
                )
            }

            item {
                HorizontalDivider()

                Text(
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    text = stringResource(id = R.string.clear_all_history),
                    modifier = Modifier
                        .padding(all = 12.dp)
                        .fillMaxWidth()
                        .clickable {
                            onHomeUiEvent(HomeUiEvent.OnDeleteAllHistoryClick)
                        }
                )
            }
        }
    }
}

@Composable
fun SearchKeyword(
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

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(
    @PreviewParameter(SearchUiStateProvider::class) items: Pair<SearchUiState, Boolean>,
) {
    AllAboutMovieTheme {
        HomeScreen(
            searchUiState = items.first,
            searchActive = items.second,
            onSearchActiveChange = {},
            onHomeUiEvent = { },
            modifier = Modifier.fillMaxSize()
        )
    }
}

