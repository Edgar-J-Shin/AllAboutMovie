package com.dcs.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.dcs.presentation.R

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    message: String = ""
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Preview(name = "ErrorScreen", showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(
        message = stringResource(id = R.string.api_response_error_message)
    )
}
