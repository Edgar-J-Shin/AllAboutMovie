package com.dcs.presentation.ui.persondetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.component.BasicImage
import com.dcs.presentation.core.model.KnownForUiState
import com.dcs.presentation.core.model.getPosterUrl

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
internal fun KnownFor(
    knownFor: List<KnownForUiState>,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.title_known_for),
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
                ElevatedCard(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.width(100.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(bottom = 12.dp)
                    ) {
                        BasicImage(
                            imageUrl = it.getPosterUrl(),
                            contentDescription = stringResource(id = R.string.content_description_poster),
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                        Text(
                            text = it.title.ifBlank { it.name },
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
