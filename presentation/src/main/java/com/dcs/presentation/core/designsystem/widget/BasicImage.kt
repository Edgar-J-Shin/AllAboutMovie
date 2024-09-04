package com.dcs.presentation.core.designsystem.widget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
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
    contentDescription: String = BasicImageDefaults.contentDescription(),
    placeHolder: Painter? = BasicImageDefaults.placeHolder(),
    error: Painter? = BasicImageDefaults.error(),
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

@Immutable
object BasicImageDefaults {

    @Composable
    fun placeHolder(): Painter = painterResource(id = R.drawable.image_placeholder)

    @Composable
    fun contentDescription(): String = stringResource(id = R.string.image_content_description)

    @Composable
    fun error(): Painter = painterResource(id = R.drawable.image_placeholder)

}
