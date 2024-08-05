package com.dcs.presentation.ui.people

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.widget.ErrorScreen
import com.dcs.presentation.core.model.PersonUiState
import com.dcs.presentation.core.model.PersonUiStateProvider
import com.dcs.presentation.core.theme.AllAboutMovieTheme
import com.dcs.presentation.core.theme.Gray1
import kotlinx.coroutines.flow.flowOf

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
                Skeleton()
            }

            is LoadState.Error -> {
                ErrorScreen(
                    message = stringResource(id = R.string.api_response_error_message),
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

@Composable
private fun Skeleton(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(color = Gray1)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(color = Gray1)
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row(
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(color = Gray1)
            )
            Spacer(modifier = Modifier.width(15.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(color = Gray1)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PeopleScreenPreview(
    @PreviewParameter(PersonUiStateProvider::class) items: PagingData<PersonUiState>,
) {
    AllAboutMovieTheme {
        val people = flowOf(items)
            .collectAsLazyPagingItems()

        PeopleScreen(
            popularPeople = people,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        )
    }
}
