package com.dcs.presentation.core.designsystem.widget

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun LoadingDialog(
    onDismissRequest: () -> Unit = {},
    circularProgressIndicator: LoadingDialogCircularProgressIndicator = LoadingDialogDefaults.circularProgressIndicator(),
    properties: DialogProperties = LoadingDialogDefaults.dialogProperties(),
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(circularProgressIndicator.size),
            color = circularProgressIndicator.color,
            trackColor = circularProgressIndicator.trackColor,
        )
    }
}

object LoadingDialogDefaults {
    @Composable
    fun dialogProperties(): DialogProperties {
        return DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false,
        )
    }

    @Composable
    fun circularProgressIndicator(): LoadingDialogCircularProgressIndicator {
        return LoadingDialogCircularProgressIndicator.DEFAULT
    }
}

@Immutable
data class LoadingDialogCircularProgressIndicator(
    @Stable val size: Dp,
    val color: Color,
    val trackColor: Color,
) {
    companion object {
        val DEFAULT
            @Composable get() = LoadingDialogCircularProgressIndicator(
                size = 64.dp,
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
    }
}
