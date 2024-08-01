package com.dcs.presentation.core.designsystem

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircularProgressBarWithPercentage(
    modifier: Modifier = Modifier,
    percentage: Int = 0,
    viewSize: Dp = 40.dp
) {
    val progress by remember { mutableIntStateOf(percentage) }

    val startAngle = 270f

    val convertedValue = progress * 360 / 100
    val insideArcSize = (viewSize - 8.dp)
    val halfSize = (viewSize / 2)

    val progressSize = (viewSize / 12)
    val progressStartPos = 4.dp
    val progressColor = if (progress < 70) Color.Yellow else Color.Green

    Box(
        modifier
            .width(viewSize)
            .height(viewSize)
    ) {
        Canvas(
            modifier = Modifier
                .width(viewSize)
                .height(viewSize)
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
                startAngle = startAngle,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(progressSize.toPx(), cap = StrokeCap.Square)
            )

            drawArc(
                topLeft = topLeft,
                size = size,
                brush = SolidColor(progressColor),
                startAngle = startAngle,
                sweepAngle = convertedValue.toFloat(),
                useCenter = false,
                style = Stroke(progressSize.toPx(), cap = StrokeCap.Round)
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.Center),
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

@Preview(name = "PercentageCircleView", showBackground = true)
@Composable
fun CircularProgressBarWithPercentagePreview() {
    CircularProgressBarWithPercentage(
        percentage = 70
    )
}
