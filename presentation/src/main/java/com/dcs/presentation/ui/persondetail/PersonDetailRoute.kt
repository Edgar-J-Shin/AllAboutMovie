package com.dcs.presentation.ui.persondetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PersonDetailScreen(
    navigateUp: () -> Unit,
    uiState: UiState<PersonDetailUiState>,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
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
                }
            )
        },
        modifier = modifier
    ) { innerPadding ->
        when (uiState) {
            is UiState.Success -> {
                val personDetailUiState = uiState.data
                PersonDetailContent(
                    personDetailUiState = personDetailUiState,
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                )
            }

            is UiState.Loading -> {
                LoadingDialog()
            }

            is UiState.Error -> {
                ErrorScreen()
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PersonDetailContent(
    personDetailUiState: PersonDetailUiState,
    modifier: Modifier = Modifier,
) {

    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
    ) {
        GlideImage(
            model = "${BuildConfig.TMDB_IMAGE_URL}original${personDetailUiState.profilePath}",
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
        )

        PersonalInfoSection(
            knownForDepartment = personDetailUiState.knownForDepartment,
            birthday = personDetailUiState.birthday,
            placeOfBirth = personDetailUiState.placeOfBirth,
            gender = personDetailUiState.gender,
            credits = personDetailUiState.casts.size
        )

        BiographySection(
            biography = personDetailUiState.biography
        )

        KnownForSection(
            personDetailUiState.knownFor
        )

    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun KnownForSection(
    knownFor: List<KnownForUiState>,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {

        Text(
            text = "Known For",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
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
    Column(modifier = modifier) {
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
    Column {
        Text(
            text = "Personal Info",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            KnownForDepartment(
                knownForDepartment = knownForDepartment,
                modifier = modifier.weight(1f),
            )

            KnownCredits(
                credits,
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        // Gender
        Gender(gender)
        Spacer(modifier = Modifier.height(12.dp))
        // Birthday
        Birthday(birthday)
        Spacer(modifier = Modifier.height(12.dp))
        // Place of Birth
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
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = knownForDepartment,
            style = MaterialTheme.typography.bodySmall
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

