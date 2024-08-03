package com.dcs.presentation.core.designsystem.widget

import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun BasicWebView(
    onUpdate: (WebView) -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val webView = remember {
        WebView(context)
    }

    val runningInPreview = LocalInspectionMode.current

    AndroidView(
        factory = { webView },
        update = {
            // WebView is not supported in preview
            if (runningInPreview) return@AndroidView
            onUpdate(it)
        },
        modifier = modifier
    )
}
