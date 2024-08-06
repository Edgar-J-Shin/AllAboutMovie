package com.dcs.presentation.ui.people

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.widget.ErrorScreen
import com.dcs.presentation.core.model.PersonUiState
import com.dcs.presentation.core.model.PersonUiStateProvider
import com.dcs.presentation.core.model.toProfileUrl
import com.dcs.presentation.core.model.toTitle
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
        items = popularPeople,
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    )
}

@Composable
private fun PeopleScreen(
    items: LazyPagingItems<PersonUiState>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.title_poplular_people),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when (items.loadState.refresh) {
            is LoadState.Loading -> {
                Skeleton()
            }

            is LoadState.Error -> {
                ErrorScreen(
                    message = stringResource(id = R.string.api_response_error_message),
                    primaryButton = {
                        Button(
                            onClick = items::retry
                        ) {
                            Text(text = stringResource(id = R.string.retry))
                        }
                    },
                )
            }

            is LoadState.NotLoading -> {
                PopularPeople(
                    items = items,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun PopularPeople(
    items: LazyPagingItems<PersonUiState>,
    modifier: Modifier = Modifier,
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(8.dp),
        verticalItemSpacing = 12.dp,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.fillMaxSize()
    ) {
        items(items.itemCount) { index ->
            val person = items[index] ?: return@items

            PersonCard(
                state = person,
                modifier = Modifier
                    .fillMaxWidth()
            )

        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun PersonCard(
    state: PersonUiState,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        )
    ) {
        GlideImage(
            model = state.toProfileUrl(),
            contentDescription = "profile image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
        )
        val knownForTitle = state.knownFor.toTitle()
        if (knownForTitle.isNotEmpty()) {
            Text(
                text = knownForTitle,
                modifier = Modifier
                    .padding(12.dp),
            )
        }
    }
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
            items = people,
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        )
    }
}
