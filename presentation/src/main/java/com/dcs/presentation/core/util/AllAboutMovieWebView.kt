package com.dcs.presentation.core.util

import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun AllAboutMovieWebView(
    onUpdate: (WebView) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val webView = remember {
        WebView(context)
    }

    AndroidView(
        factory = { webView },
        update = {
            onUpdate(it)
        },
        modifier = modifier
    )
}
