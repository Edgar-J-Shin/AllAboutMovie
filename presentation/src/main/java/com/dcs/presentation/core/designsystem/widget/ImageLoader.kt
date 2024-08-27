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
fun ImageLoader(
    imageLoaderData: ImageLoaderData,
    modifier: Modifier = Modifier,
) {
    GlideImage(
        model = imageLoaderData.imageUrl,
        loading = imageLoaderData.loadingResId?.let { placeholder(it) },
        failure = imageLoaderData.failureResId?.let { placeholder(it) },
        contentDescription = stringResource(id = imageLoaderData.contentDescResId),
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxSize()
    )
}

@Immutable
data class ImageLoaderData(
    val imageUrl: String,
    @DrawableRes val loadingResId: Int? = R.drawable.image_placeholder,
    @DrawableRes val failureResId: Int? = R.drawable.image_placeholder,
    @StringRes val contentDescResId: Int = R.string.image_content_description,
) {
    init {
        require(imageUrl.isNotEmpty())
    }
}
