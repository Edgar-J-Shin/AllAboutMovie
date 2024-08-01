package com.dcs.presentation.core.designsystem.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    dismissOnBackPress: Boolean = false,
    dismissOnClickOutside: Boolean = false,
    usePlatformDefaultWidth: Boolean = false,
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Dialog(
            onDismissRequest = {},
            properties = DialogProperties(
                dismissOnBackPress = dismissOnBackPress,
                dismissOnClickOutside = dismissOnClickOutside,
                usePlatformDefaultWidth = usePlatformDefaultWidth,
            ),
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(size),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}

@Preview(name = "LoadingScreen", showBackground = true)
@Composable
fun LoadingScreenPreview() {
    LoadingScreen()
}
