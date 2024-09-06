package com.dcs.presentation.core.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.dcs.presentation.R

@Composable
fun BasicImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    contentDescription: String = stringResource(id = R.string.image_content_description),
    placeHolder: Painter? = painterResource(id = R.drawable.image_placeholder),
    error: Painter? = painterResource(id = R.drawable.image_placeholder),
    contentScale: ContentScale = ContentScale.Fit,
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        contentScale = contentScale,
        placeholder = placeHolder,
        error = error,
        modifier = modifier
    )
}
