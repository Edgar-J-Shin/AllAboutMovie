package com.dcs.presentation.core.model

import android.net.Uri
import androidx.compose.runtime.Stable
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.dcs.domain.model.RequestToken
import com.dcs.presentation.core.state.UiState

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

    constructor(baseUrl: String, requestToken: RequestToken, loading: Boolean) : this(
        requestToken = requestToken,
        url = requestToken.buildUrl(baseUrl),
        loading = loading,
    )
}

fun RequestToken.buildUrl(baseUrl: String): Uri {
    return Uri.parse(baseUrl)
        .buildUpon()
        .appendPath(this.value)
        .build()
}

class SignInUiStateProvider :
    PreviewParameterProvider<UiState<SignInUiState>> {
    override val values: Sequence<UiState<SignInUiState>>
        get() = sequenceOf(
            UiState.Success(
                SignInUiState(
                    requestToken = RequestToken("request_token"),
                    url = Uri.parse("https://www.themoviedb.org/auth/access?request_token=request_token"),
                    loading = true
                )
            ),
            UiState.Error(Exception())
        )

}
