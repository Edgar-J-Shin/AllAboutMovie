package com.dcs.presentation.core.model

import android.net.Uri
import androidx.compose.runtime.Stable
import com.dcs.domain.model.RequestToken

@Stable
data class SignInUiState(
    val requestToken: RequestToken,
    val url: Uri,
    val loading: Boolean = false,
) {
    constructor(baseUrl: String, requestToken: RequestToken) : this(
        requestToken = requestToken,
        url = requestToken.buildUrl(baseUrl),
    )
}

fun RequestToken.buildUrl(baseUrl: String): Uri {
    return Uri.parse(baseUrl)
        .buildUpon()
        .appendPath(this.value)
        .build()
}
