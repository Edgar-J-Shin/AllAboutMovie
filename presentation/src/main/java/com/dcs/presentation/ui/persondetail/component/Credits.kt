package com.dcs.presentation.ui.persondetail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dcs.presentation.R
import com.dcs.presentation.core.model.CastUiState
import com.dcs.presentation.core.model.CrewUiState
import com.dcs.presentation.core.model.getActingTitle
import com.dcs.presentation.core.model.getCharacterTitle
import com.dcs.presentation.core.model.getJobTitle
import com.dcs.presentation.core.model.getProductionTitle
import com.dcs.presentation.ui.persondetail.PersonDetailUiEvent
import com.dcs.presentation.ui.persondetail.spacer

internal fun LazyListScope.credits(
    casts: List<CastUiState>,
    crews: List<CrewUiState>,
    onPersonDetailUiEvent: (PersonDetailUiEvent) -> Unit,
) {
    if (casts.isNotEmpty()) {
        item {
            Text(
                text = stringResource(id = R.string.title_acting),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
        }

        items(
            items = casts,
        ) { cast ->
            ActingCard(
                cast = cast,
                onClick = {
                    onPersonDetailUiEvent(
                        PersonDetailUiEvent.OnActingCardClick(
                            id = cast.id,
                            mediaType = cast.mediaType
                        )
                    )
                }
            )
        }

        spacer()
    }

    if (crews.isNotEmpty()) {
        item {
            Text(
                text = stringResource(id = R.string.title_production),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
        }

        items(
            items = crews,
        ) { crew ->
            ProductionCard(
                crew = crew,
                onClick = {
                    onPersonDetailUiEvent(
                        PersonDetailUiEvent.OnProductionCardClick(
                            id = crew.id,
                            mediaType = crew.mediaType
                        )
                    )
                }
            )
        }

        spacer()
    }
}

@Composable
private fun ActingCard(
    cast: CastUiState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                val y = size.height - strokeWidth / 2
                drawLine(
                    color = Color(0xFFBFBFBF),
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }
            .padding(vertical = 12.dp)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = cast.getActingTitle(),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = cast.getCharacterTitle(),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 20.dp)
        )
    }
}

@Composable
private fun ProductionCard(
    crew: CrewUiState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .drawBehind {
                val strokeWidth = 1.dp.toPx()
                val y = size.height - strokeWidth / 2
                drawLine(
                    color = Color(0xFFBFBFBF),
                    start = Offset(0f, y),
                    end = Offset(size.width, y),
                    strokeWidth = strokeWidth
                )
            }
            .padding(vertical = 12.dp)
            .clickable(onClick = onClick)
    ) {
        Text(
            text = crew.getProductionTitle(),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = crew.getJobTitle(),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 20.dp)
        )
    }
}
