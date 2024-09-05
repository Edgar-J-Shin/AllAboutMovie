package com.dcs.presentation.ui.trend.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.widget.BasicImage
import com.dcs.presentation.core.designsystem.widget.CircularProgressBarWithPercentage
import com.dcs.presentation.core.model.MediaContentUiState
import com.dcs.presentation.core.model.getPosterPathUrl
import com.dcs.presentation.core.model.getVotePercentage

@Composable
fun MediaItem(
    modifier: Modifier = Modifier,
    mediaContentUiState: MediaContentUiState,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 4.dp)
            .clickable(onClick = onClick)
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(0.75f)
        ) {
            BasicImage(
                imageUrl = mediaContentUiState.getPosterPathUrl(),
                contentDescription = stringResource(R.string.movie_image_content_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 4.dp,
                        end = 4.dp,
                        top = 4.dp,
                        bottom = 20.dp
                    )
                    .align(Alignment.TopCenter)
                    .clip(RoundedCornerShape(8.dp))
            )

            CircularProgressBarWithPercentage(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 10.dp),
                percentage = mediaContentUiState.getVotePercentage(),
                viewSize = 40.dp
            )
        }

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            text = mediaContentUiState.title,
            textAlign = TextAlign.Left,
            maxLines = 1,
            fontSize = 12.sp,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
        )

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            text = mediaContentUiState.releaseDate,
            textAlign = TextAlign.Left,
            maxLines = 1,
            fontSize = 12.sp,
            color = Color.Gray,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

