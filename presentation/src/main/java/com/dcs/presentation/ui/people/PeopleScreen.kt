package com.dcs.presentation.ui.people

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.widget.ErrorScreen
import com.dcs.presentation.core.model.PersonUiState

@Composable
fun PeopleRoute(
    modifier: Modifier = Modifier,
    viewModel: PeopleViewModel = hiltViewModel(),
) {

    val popularPeople = viewModel.popularPeople.collectAsLazyPagingItems()

    PeopleScreen(
        popularPeople = popularPeople,
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    )
}

@Composable
private fun PeopleScreen(
    popularPeople: LazyPagingItems<PersonUiState>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.title_poplular_people),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when (popularPeople.loadState.refresh) {
            is LoadState.Loading -> {
                // LoadingScreen()
            }

            is LoadState.Error -> {
                ErrorScreen(
                    primaryButton = {
                        Button(
                            onClick = popularPeople::retry
                        ) {
                            Text(text = stringResource(id = R.string.retry))
                        }
                    },
                )
            }

            is LoadState.NotLoading -> {
                PopularPeople(
                    popularPeople = popularPeople,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun PopularPeople(
    popularPeople: LazyPagingItems<PersonUiState>,
    modifier: Modifier = Modifier,
) {
}
