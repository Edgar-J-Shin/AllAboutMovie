package com.dcs.presentation.ui.persondetail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.component.BasicImage
import com.dcs.presentation.core.model.PersonDetailUiState
import com.dcs.presentation.core.model.getProfileUrl
import com.dcs.presentation.ui.persondetail.component.Biography
import com.dcs.presentation.ui.persondetail.component.KnownFor
import com.dcs.presentation.ui.persondetail.component.PersonalInfo
import com.dcs.presentation.ui.persondetail.component.credits


@Composable
internal fun PersonDetailContent(
    personDetailUiState: PersonDetailUiState,
    contentPaddingValue: PaddingValues,
    scrollState: LazyListState,
    onPersonDetailUiEvent: (PersonDetailUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = contentPaddingValue,
        state = scrollState,
        modifier = modifier
    ) {
        item {
            BasicImage(
                imageUrl = personDetailUiState.getProfileUrl(),
                contentDescription = stringResource(id = R.string.content_description_profile),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = 50.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        spacer()

        item {
            PersonalInfo(
                knownForDepartment = personDetailUiState.knownForDepartment,
                birthday = personDetailUiState.birthday,
                placeOfBirth = personDetailUiState.placeOfBirth,
                gender = personDetailUiState.gender,
                credits = personDetailUiState.casts.size,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
        }

        spacer()

        item {
            Biography(
                biography = personDetailUiState.biography,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
        }

        spacer()

        item {
            KnownFor(
                knownFor = personDetailUiState.knownFor,
                onPersonDetailUiEvent = onPersonDetailUiEvent,
            )
        }

        spacer()

        credits(
            casts = personDetailUiState.casts,
            crews = personDetailUiState.crews,
            onPersonDetailUiEvent = onPersonDetailUiEvent,
        )
    }
}

internal fun LazyListScope.spacer(height: Dp = 20.dp) {
    item {
        Spacer(modifier = Modifier.height(height))
    }
}
