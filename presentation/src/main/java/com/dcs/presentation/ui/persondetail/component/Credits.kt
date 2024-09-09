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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dcs.presentation.core.model.PersonCreditUiState
import com.dcs.presentation.core.model.getActingTitle
import com.dcs.presentation.core.model.getCharacterTitle
import com.dcs.presentation.ui.persondetail.PersonDetailUiEvent
import com.dcs.presentation.ui.persondetail.spacer

internal fun LazyListScope.credits(
    credits: Map<String, List<PersonCreditUiState>>,
    onPersonDetailUiEvent: (PersonDetailUiEvent) -> Unit,
) {
    credits.keys.sorted().forEach { key ->
        item {
            Text(
                text = key,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 30.dp)
            )
        }

        val items = credits[key] ?: return@forEach
        items(
            items = items,
        ) { item ->

            CreditCard(
                item = item,
                onClick = {
                    onPersonDetailUiEvent(
                        PersonDetailUiEvent.OnActingCardClick(
                            id = item.id,
                            mediaType = item.mediaType
                        )
                    )
                }
            )
        }

        spacer()
    }
}

@Composable
private fun CreditCard(
    item: PersonCreditUiState,
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
            text = item.getActingTitle(),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = item.getCharacterTitle(),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(start = 20.dp)
        )
    }
}
