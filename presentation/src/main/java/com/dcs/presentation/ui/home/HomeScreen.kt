package com.dcs.presentation.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {

    Scaffold(
        topBar = {

        }
    ) { innerPadding ->
        Box(
            modifier = modifier.padding(innerPadding)
        ) {

        }
    }
}
