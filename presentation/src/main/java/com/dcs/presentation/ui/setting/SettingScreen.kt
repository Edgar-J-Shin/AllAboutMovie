package com.dcs.presentation.ui.setting

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
import androidx.navigation.NavHostController
import com.dcs.presentation.core.extensions.collectAsEffect
import com.dcs.presentation.ui.Screen

@Composable
fun SettingRoute(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    viewModel.effect.collectAsEffect { effect ->
        when (effect) {
            SettingEffect.SignIn -> {
                navController.navigate(Screen.SignIn.route)
            }
        }
    }

    SettingScreen(
        onSettingEvent = viewModel::dispatch,
        modifier = modifier.fillMaxSize(),
    )
}

@Composable
fun SettingScreen(
    onSettingEvent: (SettingEvent) -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        SignInButton(
            onClick = { onSettingEvent(SettingEvent.SignIn) },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun SignInButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            shape = RoundedCornerShape(8.dp),
            onClick = onClick
        ) {
            Text(text = "로그인 하기")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingScreen() {
    SettingScreen(
        onSettingEvent = {},
        modifier = Modifier.fillMaxSize(),
    )
}
