package com.dcs.presentation.movieItem

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dcs.presentation.core.extentions.toImageUrl
import com.dcs.presentation.core.model.MovieItemUiState

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MovieItem(
    movie: MovieItemUiState,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val imageRatio = 3f / 4f

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick.invoke() }
    ) {

        Box(
            modifier = modifier
                .background(Color.Red)
                .fillMaxWidth()
        ) {

            GlideImage(
                model = movie.backdropPath.toImageUrl(),
                contentDescription = "",
                modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
                    .align(Alignment.TopCenter)
                    .aspectRatio(imageRatio)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            PercentageCircleView(
                modifier = modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 10.dp, top = 20.dp),
                viewSize = 40.dp,
                percentage = (movie.voteAverage * 10).toInt()
            )
        }

        Text(
            text = movie.title,
            textAlign = TextAlign.Left,
            maxLines = 1,
            fontSize = 12.sp,
            color = Color.Black,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        )

        Text(
            text = movie.releaseDate,
            textAlign = TextAlign.Left,
            maxLines = 1,
            fontSize = 12.sp,
            color = Color.Gray,
            overflow = TextOverflow.Ellipsis,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        )
    }
}

@Preview(name = "PercentageCircleView", showBackground = true)
@Composable
fun PercentageCircleView(
    percentage: Int = 0,
    viewSize: Dp = 40.dp,
    modifier: Modifier = Modifier
) {
    val progress by remember { mutableStateOf(percentage) }

    val convertedValue = progress * 360 / 100
    val insideArcSize = (viewSize - 8.dp)
    val halfSize = (viewSize / 2)

    val progressSize = (viewSize / 12)
    val progressStartPos = 4.dp
    val progressColor = if (progress < 70) Color.Yellow else Color.Green

    Box(
        modifier = modifier
            .size(viewSize)
            .aspectRatio(1f)
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {

            drawCircle(
                color = Color.DarkGray,
                center = Offset(x = halfSize.toPx(), y = halfSize.toPx()),
                radius = halfSize.toPx()
            )

            val topLeft = Offset(progressStartPos.toPx(), progressStartPos.toPx())
            val size = Size(insideArcSize.toPx(), insideArcSize.toPx())

            drawArc(
                topLeft = topLeft,
                size = size,
                brush = SolidColor(Color.Gray),
                startAngle = 270f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(progressSize.toPx(), cap = StrokeCap.Square)
            )

            drawArc(
                topLeft = topLeft,
                size = size,
                brush = SolidColor(progressColor),
                startAngle = 270f,
                sweepAngle = convertedValue.toFloat(),
                useCenter = false,
                style = Stroke(progressSize.toPx(), cap = StrokeCap.Round)
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(0.dp)
        ) {
            Text(
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                text = "$progress"
            )
            Text(
                color = Color.White,
                fontSize = 6.sp,
                text = "%"
            )
        }
    }
}