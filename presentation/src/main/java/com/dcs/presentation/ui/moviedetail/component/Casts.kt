package com.dcs.presentation.ui.moviedetail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.widget.BasicImage
import com.dcs.presentation.core.designsystem.widget.BasicImageDefaults
import com.dcs.presentation.core.designsystem.widget.ErrorScreen
import com.dcs.presentation.core.model.CreditsCastUiState
import com.dcs.presentation.core.model.getProfilePathUrl

@Composable
internal fun Casts(
    casts: List<CreditsCastUiState>,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.top_billed_cast),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        )

        if (casts.isEmpty()) {
            ErrorScreen(
                message = stringResource(id = R.string.top_billed_cast_empty_message),
                textStyle = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(
                    vertical = 12.dp
                )
            )
        } else {
            LazyRow(
                contentPadding = PaddingValues(
                    horizontal = 6.dp,
                    vertical = 12.dp
                ),
                state = rememberLazyListState(),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(
                    count = casts.count()
                ) { index ->
                    CastItem(
                        cast = casts[index],
                        modifier = Modifier
                            .width(140.dp)
                            .aspectRatio(0.6f)
                            .padding(horizontal = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
internal fun CastItem(
    cast: CreditsCastUiState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() }
    ) {
        BasicImage(
            imageUrl = cast.getProfilePathUrl(),
            contentDescription = BasicImageDefaults.contentDescription(id = R.string.cast_image_content_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(0.9f)
        )

        Text(
            text = cast.name,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 16.dp
                )
        )

        Text(
            text = cast.character,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            modifier = Modifier
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = 4.dp
                )
        )
    }
}
