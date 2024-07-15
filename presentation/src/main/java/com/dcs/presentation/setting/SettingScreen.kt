package com.dcs.presentation.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingRoute(
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    SettingScreen(
        modifier = modifier.fillMaxSize(),
    )
}

@Composable
fun SettingScreen(
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        SignInButton(
            Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun SignInButton(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            shape = RoundedCornerShape(8.dp),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "로그인 하기")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingScreen() {
    SettingScreen(
        modifier = Modifier.fillMaxSize(),
    )
}
