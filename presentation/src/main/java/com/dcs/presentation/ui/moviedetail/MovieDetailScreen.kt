package com.dcs.presentation.ui.moviedetail

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.dcs.presentation.core.extensions.collectAsEffect
import com.dcs.presentation.core.theme.AllAboutMovieTheme

@Composable
fun MovieDetailRoute(
    navController: NavHostController,
    viewModel: MovieDetailViewModel = hiltViewModel(),
    showSnackBar: (String, SnackbarDuration) -> Unit = { _, _ -> },
) {
    val context = LocalContext.current

    viewModel.effect.collectAsEffect { effect ->
        when (effect) {
            is MovieDetailEffect.NavigateBack -> {
                navController.popBackStack()
            }

            is MovieDetailEffect.ShowSnackbar -> {
                showSnackBar(
                    context.getString(effect.state.messageResId),
                    effect.state.duration
                )
            }
        }
    }

    MovieDetailScreen(
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun MovieDetailScreen(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(
//    @PreviewParameter(MovieItemUiStateProvider::class) items: Pair<SearchUiState, Boolean>,
) {
    AllAboutMovieTheme {
        MovieDetailScreen(

            modifier = Modifier.fillMaxSize()
        )
    }
}

