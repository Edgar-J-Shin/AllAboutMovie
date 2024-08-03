package com.dcs.presentation.ui.trend

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dcs.presentation.core.designsystem.widget.CircularProgressBarWithPercentage
import com.dcs.presentation.core.extensions.toImageUrl
import com.dcs.presentation.core.model.MovieItemUiState

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: MovieItemUiState,
    onClick: () -> Unit = {}
) {
    Column(modifier = modifier
        .fillMaxWidth()
        .padding(all = 4.dp)
        .clickable { onClick.invoke() }
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(0.75f)
        ) {
            GlideImage(
                model = movie.posterPath.toImageUrl(),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 4.dp, end = 4.dp, top = 4.dp, bottom = 20.dp)
                    .align(Alignment.TopCenter)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            CircularProgressBarWithPercentage(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 10.dp),
                percentage = (movie.voteAverage * 10).toInt(),
                viewSize = 40.dp
            )
        }

        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            text = movie.title.ifEmpty { movie.name },
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
            text = movie.releaseDate.ifEmpty { movie.firstAirDate },
            textAlign = TextAlign.Left,
            maxLines = 1,
            fontSize = 12.sp,
            color = Color.Gray,
            overflow = TextOverflow.Ellipsis,
        )
    }
}
