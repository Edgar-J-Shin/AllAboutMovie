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

class SignInUiStateProvider :
    PreviewParameterProvider<Pair<UiState<SignInUiState>, Boolean>> {
    override val values: Sequence<Pair<UiState<SignInUiState>, Boolean>>
        get() = sequenceOf(
            UiState.Success(
                SignInUiState(
                    requestToken = RequestToken("request_token"),
                    url = Uri.parse("https://www.themoviedb.org/auth/access?request_token=request_token"),
                )
            ) to true,
            UiState.Error(Exception()) to false
        )

}
