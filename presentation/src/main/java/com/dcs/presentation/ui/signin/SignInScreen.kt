package com.dcs.presentation.ui.signin

import android.annotation.SuppressLint
import android.net.Uri
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.dcs.presentation.core.designsystem.BasicWebView
import com.dcs.presentation.core.designsystem.LoadingScreen
import com.dcs.presentation.core.model.SignInUiState
import com.dcs.presentation.core.state.UiState
import com.dcs.presentation.core.theme.AllAboutMovieTheme
import com.dcs.presentation.core.theme.Gray1
import com.dcs.presentation.core.theme.White3

private const val PATH_ALLOW = "allow"

@Composable
fun SignInRoute(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel(),
) {

    val uiState by viewModel.signInState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.effect.collect {
            when (it) {
                is SignInEffect.Finish -> {
                    navController.popBackStack()
                }
            }
        }
    }

    SignInScreen(
        state = uiState,
        onSignInEvent = viewModel::dispatch,
        modifier = modifier
            .systemBarsPadding()
            .background(color = White3)
    )
}

@Composable
private fun SignInScreen(
    state: UiState<SignInUiState>,
    onSignInEvent: (SignInEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        when (state) {
            is UiState.Loading -> {
                // Loading screen
                Skeleton()
            }

            is UiState.Success -> {
                val signInUiState = state.data
                if (signInUiState != null) {
                    SignInContents(
                        signInUiState = signInUiState,
                        onSignInEvent = onSignInEvent,
                        modifier = Modifier
                            .systemBarsPadding()
                            .background(color = White3)
                    )

                    if (signInUiState.loading) {
                        LoadingScreen()
                    }
                }
            }

            is UiState.Error -> {
                // Error screen
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun SignInContents(
    signInUiState: SignInUiState,
    onSignInEvent: (SignInEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    BasicWebView(
        onUpdate = { webView ->
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    val uri = url?.let { Uri.parse(it) } ?: return
                    if (uri.host == signInUiState.url.host && uri.lastPathSegment == PATH_ALLOW) {
                        // Handle request token
                        onSignInEvent(
                            SignInEvent.SignIn(
                                requestToken = signInUiState.requestToken,
                            )
                        )
                    }
                }
            }
            webView.settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                cacheMode = WebSettings.LOAD_NO_CACHE
            }
            webView.loadUrl(signInUiState.url.toString())
        },
        modifier = modifier.fillMaxSize()
    )
}

@Composable
private fun Skeleton(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
    ) {
        // 1 small box and 2 big boxes
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(50.dp)
                .background(color = Gray1)
        )

        Box(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Gray1)
        )

        Box(
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth()
                .height(100.dp)
                .background(color = Gray1)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun SignInSkeletonPreview() {
    AllAboutMovieTheme {
        Skeleton()
    }
}
