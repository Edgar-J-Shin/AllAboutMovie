package com.dcs.presentation.ui.persondetail

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dcs.presentation.core.model.PersonDetailUiState
import com.dcs.presentation.ui.persondetail.component.Biography
import com.dcs.presentation.ui.persondetail.component.KnownFor
import com.dcs.presentation.ui.persondetail.component.PersonalInfo
import com.dcs.presentation.ui.persondetail.component.credits

internal fun LazyListScope.personDetailContent(
    personDetailUiState: PersonDetailUiState,
    onPersonDetailUiEvent: (PersonDetailUiEvent) -> Unit,
    profileImage: @Composable LazyItemScope.() -> Unit,
) {
    item {
        profileImage()
    }

    spacer()

    item {
        PersonalInfo(
            knownForDepartment = personDetailUiState.knownForDepartment,
            birthday = personDetailUiState.birthday,
            placeOfBirth = personDetailUiState.placeOfBirth,
            gender = personDetailUiState.gender,
            credits = personDetailUiState.creditCounts,
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
        credits = personDetailUiState.credits,
        onPersonDetailUiEvent = onPersonDetailUiEvent,
    )
}

internal fun LazyListScope.spacer(height: Dp = 20.dp) {
    item {
        Spacer(modifier = Modifier.height(height))
    }
}
