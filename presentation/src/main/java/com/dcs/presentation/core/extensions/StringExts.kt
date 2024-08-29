package com.dcs.presentation.core.extensions

import com.dcs.presentation.BuildConfig
import java.util.Locale

enum class ImageType {
    ORIGINAL, W500, W200;

    override fun toString(): String {
        return super.toString().lowercase(Locale.getDefault())
    }
}
