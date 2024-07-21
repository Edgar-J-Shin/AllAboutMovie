package com.dcs.presentation.core.extensions

import com.dcs.presentation.BuildConfig
import java.util.Locale

enum class ImageType {
    ORIGINAL, W500;

    override fun toString(): String {
        return super.toString().lowercase(Locale.getDefault())
    }
}

fun String.toImageUrl(imageType: ImageType = ImageType.W500) = "${BuildConfig.TMDB_IMAGE_URL}$imageType$this"
