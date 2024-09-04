package com.dcs.presentation.ui.moviedetail.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.widget.BasicImage

@Composable
fun MovieImage(
    backdropPathUrl: String,
    posterPathUrl: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
    ) {
        BasicImage(
            imageUrl = backdropPathUrl,
            contentDescription = stringResource(R.string.movie_image_content_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
        )

        BasicImage(
            imageUrl = posterPathUrl,
            contentDescription = stringResource(R.string.movie_image_content_description),
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(0.75f)
                .padding(all = 20.dp)
                .align(Alignment.CenterStart)
                .clip(RoundedCornerShape(8.dp)),
        )
    }
}
