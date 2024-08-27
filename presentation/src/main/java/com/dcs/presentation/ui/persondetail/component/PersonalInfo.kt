package com.dcs.presentation.ui.persondetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dcs.presentation.R
import com.dcs.presentation.core.model.GenderUiState
import com.dcs.presentation.core.model.toGenderString

@Composable
internal fun PersonalInfo(
    knownForDepartment: String,
    birthday: String,
    placeOfBirth: String,
    gender: GenderUiState,
    credits: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.title_personal_info),
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
            text = stringResource(id = R.string.title_known_for_department),
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
            text = stringResource(id = R.string.title_credits),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = credits.toString(),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
private fun Gender(
    gender: GenderUiState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.title_gender),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = gender.toGenderString(),
            style = MaterialTheme.typography.labelMedium
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
            text = stringResource(id = R.string.title_birthday),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = birthday,
            style = MaterialTheme.typography.labelMedium
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
            text = stringResource(id = R.string.title_place_of_birth),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = placeOfBirth,
            style = MaterialTheme.typography.labelMedium
        )
    }
}
