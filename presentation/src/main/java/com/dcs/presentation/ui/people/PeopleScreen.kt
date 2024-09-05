package com.dcs.presentation.ui.people

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.component.BasicImage
import com.dcs.presentation.core.designsystem.component.ErrorScreen
import com.dcs.presentation.core.extensions.collectAsEffect
import com.dcs.presentation.core.model.PersonUiState
import com.dcs.presentation.core.model.PersonUiStateProvider
import com.dcs.presentation.core.model.getKnownForTitle
import com.dcs.presentation.core.model.getProfileUrl
import com.dcs.presentation.core.theme.AllAboutMovieTheme
import com.dcs.presentation.core.theme.Gray1
import kotlinx.coroutines.flow.flowOf

@Composable
fun PeopleRoute(
    navigateToDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PeopleViewModel = hiltViewModel(),
    showSnackBar: (String, SnackbarDuration) -> Unit = { _, _ -> },
) {

    val popularPeople = viewModel.popularPeople.collectAsLazyPagingItems()
    viewModel.effect.collectAsEffect {
        when (it) {
            is PeopleEffect.NavigateToDetail -> {
                navigateToDetail(it.personId)
            }
        }
    }

    PeopleScreen(
        items = popularPeople,
        onPeopleUiEvent = viewModel::dispatchEvent,
        modifier = modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
    )
}

@Composable
private fun PeopleScreen(
    items: LazyPagingItems<PersonUiState>,
    onPeopleUiEvent: (PeopleUiEvent) -> Unit,
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
                    onPeopleUiEvent = onPeopleUiEvent,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
private fun PopularPeople(
    items: LazyPagingItems<PersonUiState>,
    onPeopleUiEvent: (PeopleUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier,
    ) {
        items(items.itemCount) { index ->
            val person = items[index] ?: return@items

            PersonCard(
                state = person,
                onClick = { onPeopleUiEvent(PeopleUiEvent.NavigateToDetail(person)) },
                modifier = Modifier
                    .fillMaxWidth()
            )

        }
    }
}

@Composable
private fun PersonCard(
    state: PersonUiState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        BasicImage(
            imageUrl = state.getProfileUrl(),
            contentDescription = stringResource(id = R.string.content_description_profile),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        )

        Text(
            text = state.name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(12.dp)
        )

        Text(
            text = state.getKnownForTitle(),
            maxLines = 2,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(12.dp)
                .height(60.dp),
        )
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
            onPeopleUiEvent = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        )
    }
}
