package com.dcs.presentation.ui.home

import android.view.ViewTreeObserver
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dcs.presentation.R
import kotlinx.coroutines.flow.StateFlow

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
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

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTopBar(
    queryText: StateFlow<String>,
    onQueryChange: (String) -> Unit,
    onQueryClear: () -> Unit
) {
    val searchText by queryText.collectAsStateWithLifecycle()
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
        query = searchText,
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

