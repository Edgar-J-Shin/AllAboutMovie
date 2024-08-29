package com.dcs.presentation.core.designsystem.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.dcs.presentation.R

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun BasicImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
    basicImageState: BasicImageState = BasicImageState(),
) {
    GlideImage(
        model = imageUrl,
        loading = basicImageState.loadingResId?.let { placeholder(it) },
        failure = basicImageState.failureResId?.let { placeholder(it) },
        contentDescription = stringResource(id = basicImageState.contentDescResId),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxSize()
    )
}

@Immutable
data class BasicImageState(
    @DrawableRes val loadingResId: Int? = BasicImageDefaults.placeholderResId(),
    @DrawableRes val failureResId: Int? = BasicImageDefaults.placeholderResId(),
    @StringRes val contentDescResId: Int = BasicImageDefaults.contentDescriptionResId(),
)

object BasicImageDefaults {
    @DrawableRes
    fun placeholderResId(): Int = R.drawable.image_placeholder

    @StringRes
    fun contentDescriptionResId(): Int = R.drawable.image_placeholder
}
