package com.dcs.presentation.ui.signin

import android.annotation.SuppressLint
import android.net.Uri
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.widget.BasicWebView
import com.dcs.presentation.core.designsystem.widget.ErrorScreen
import com.dcs.presentation.core.designsystem.widget.LoadingScreen
import com.dcs.presentation.core.model.SignInUiState
import com.dcs.presentation.core.model.SignInUiStateProvider
import com.dcs.presentation.core.state.UiState
import com.dcs.presentation.core.theme.AllAboutMovieTheme
import com.dcs.presentation.core.theme.White3

private const val PATH_ALLOW = "allow"

@Composable
fun SignInRoute(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel(),
) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.effect.collect {
            when (it) {
                is SignInEffect.NavigateBack -> {
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
    onSignInEvent: (SignInUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        when (state) {
            is UiState.Loading -> {
                // Loading screen
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
                ErrorScreen(
                    message = stringResource(R.string.error_message),
                    primaryButton = {
                        Button(
                            onClick = {
                                onSignInEvent(SignInUiEvent.NavigateBack)
                            },
                        ) {
                            Text(text = stringResource(R.string.close))
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun SignInContents(
    signInUiState: SignInUiState,
    onSignInEvent: (SignInUiEvent) -> Unit,
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
                            SignInUiEvent.SignIn(
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
@Preview(showBackground = true)
fun SignInScreenPreview(
    @PreviewParameter(SignInUiStateProvider::class) state: UiState<SignInUiState>,
) {
    AllAboutMovieTheme {
        SignInScreen(
            state = state,
            onSignInEvent = {},
        )
    }
}
