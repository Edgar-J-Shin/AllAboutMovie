package com.dcs.presentation.ui.signin

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.dcs.presentation.core.util.AllAboutMovieWebView

@Composable
fun SignInRoute(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    AllAboutMovieWebView(
        onUpdate = { webView ->
            webView.loadUrl("https://www.themoviedb.org/")
        },
        modifier = modifier.fillMaxSize()
    )
}
