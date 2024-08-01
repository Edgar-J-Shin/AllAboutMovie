package com.dcs.presentation.core.designsystem.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dcs.presentation.R

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    message: String = "",
    primaryButton: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = message,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
        if (primaryButton != null) {
            Spacer(modifier = Modifier.height(15.dp))
        }
        primaryButton?.invoke()
    }
}

@Preview(name = "ErrorScreen", showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(
        message = stringResource(id = R.string.api_response_error_message),
        primaryButton = {
            Button(
                onClick = {},
                modifier = Modifier
            ) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    )
}
