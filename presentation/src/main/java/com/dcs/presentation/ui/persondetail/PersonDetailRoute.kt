package com.dcs.presentation.ui.persondetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dcs.presentation.BuildConfig
import com.dcs.presentation.core.designsystem.widget.ErrorScreen
import com.dcs.presentation.core.designsystem.widget.LoadingDialog
import com.dcs.presentation.core.model.KnownForUiState
import com.dcs.presentation.core.state.UiState

@Composable
fun PersonDetailRoute(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PersonDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PersonDetailScreen(
        navigateUp = navigateUp,
        uiState = uiState,
        modifier = modifier.fillMaxSize(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PersonDetailScreen(
    navigateUp: () -> Unit,
    uiState: UiState<PersonDetailUiState>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 30.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        item {
            CenterAlignedTopAppBar(
                title = {
                    if (uiState is UiState.Success) {
                        val personDetailUiState = uiState.data
                        Text(
                            text = personDetailUiState.name,
                            style = MaterialTheme.typography.titleLarge,
                        )
                    }
                },

                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        when (uiState) {
            is UiState.Loading -> {
                item { LoadingDialog() }
            }

            is UiState.Error -> {
                item {
                    ErrorScreen()
                }
            }

            is UiState.Success -> {
                val personDetailUiState = uiState.data
                personDetailContent(
                    personDetailUiState = personDetailUiState,
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
fun LazyListScope.personDetailContent(
    personDetailUiState: PersonDetailUiState,
) {
    item {
        GlideImage(
            model = "${BuildConfig.TMDB_IMAGE_URL}original${personDetailUiState.profilePath}",
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(horizontal = 50.dp)
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
        )
    }

    item {
        PersonalInfoSection(
            knownForDepartment = personDetailUiState.knownForDepartment,
            birthday = personDetailUiState.birthday,
            placeOfBirth = personDetailUiState.placeOfBirth,
            gender = personDetailUiState.gender,
            credits = personDetailUiState.casts.size,
            modifier = Modifier.padding(horizontal = 30.dp)
        )
    }

    item {
        BiographySection(
            biography = personDetailUiState.biography,
            modifier = Modifier.padding(horizontal = 30.dp)
        )
    }

    item {
        KnownForSection(
            knownFor = personDetailUiState.knownFor,
        )
    }

    actingSection(
        casts = personDetailUiState.casts,
        crews = personDetailUiState.crews,
    )
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun KnownForSection(
    knownFor: List<KnownForUiState>,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        Text(
            text = "Known For",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 30.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 30.dp),
        ) {
            items(
                items = knownFor,
                key = { it.id }
            ) {
                Column(
                    modifier = Modifier.width(100.dp)
                ) {
                    GlideImage(
                        model = "${BuildConfig.TMDB_IMAGE_URL}original${it.posterPath}",
                        contentDescription = "Known For Image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                    )

                    Text(
                        text = it.title.ifBlank { it.name },
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

}

@Composable
private fun BiographySection(
    biography: String,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        Text(
            text = "Biography",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = biography,
            style = MaterialTheme.typography.bodySmall
        )
    }

}

@Composable
private fun PersonalInfoSection(
    knownForDepartment: String,
    birthday: String,
    placeOfBirth: String,
    gender: Int,
    credits: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        Text(
            text = "Personal Info",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            KnownForDepartment(
                knownForDepartment = knownForDepartment,
                modifier = Modifier.weight(1f),
            )

            KnownCredits(
                credits,
                modifier = Modifier.weight(1f)
            )
        }
        Gender(gender)
        Birthday(birthday)
        PlaceOfBirth(placeOfBirth)
    }
}

@Composable
private fun KnownForDepartment(
    knownForDepartment: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "Known For",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = knownForDepartment,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
private fun KnownCredits(
    credits: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Credits",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = credits.toString(),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun Gender(
    gender: Int,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "Gender",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = when (gender) {
                1 -> "Male"
                2 -> "Female"
                3 -> "Non-Binary"
                else -> "Not Specified"
            },
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun Birthday(
    birthday: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "Birthday",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = birthday,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun PlaceOfBirth(
    placeOfBirth: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "Place of Birth",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = placeOfBirth,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

private fun LazyListScope.actingSection(
    casts: List<CastUiState>,
    crews: List<CrewUiState>,
) {
    item {
        Text(
            text = "Acting",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 30.dp)
        )
    }

    items(
        items = casts,
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 30.dp)
        ) {
            Text(
                text = it.releaseDate.ifBlank { it.firstAirDate } + " " + it.name.ifBlank { it.title },
                fontWeight = FontWeight.Bold
            )
            val character = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Light
                    )
                ) {
                    append("As ")
                }
                append(it.character)
            }
            Text(
                text = character,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
    }

    item {
        HorizontalDivider(
            thickness = 2.dp,
            modifier = Modifier.padding(horizontal = 30.dp)
        )
    }

    item {
        Text(
            text = "Production",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 30.dp)
        )
    }

    items(
        items = crews,
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 30.dp)
        ) {
            Text(
                text = it.releaseDate + " " + it.title.ifBlank { it.originalTitle },
                fontWeight = FontWeight.Bold
            )
            val job = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontWeight = FontWeight.Light
                    )
                ) {
                    append("As ")
                }
                append(it.job)
            }
            Text(
                text = job,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
    }
}

