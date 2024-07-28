package com.dcs.presentation.ui.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dcs.presentation.R
import com.dcs.presentation.core.extensions.collectAsEffect
import com.dcs.presentation.core.model.SettingUiState
import com.dcs.presentation.core.model.SettingUiStateProvider
import com.dcs.presentation.core.model.UserProfile
import com.dcs.presentation.core.state.UiState
import com.dcs.presentation.core.theme.Gray1
import com.dcs.presentation.ui.Screen

@Composable
fun SettingRoute(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.effect.collectAsEffect { effect ->
        when (effect) {
            SettingEffect.SignIn -> {
                navController.navigate(Screen.SignIn.route)
            }
        }
    }

    when (state) {
        is UiState.Loading -> {
            // Loading screen
        }

        is UiState.Success -> {
            val settingUiState = (state as UiState.Success<SettingUiState>).data
            if (settingUiState != null) {
                SettingScreen(
                    uiState = settingUiState,
                    onSettingEvent = viewModel::dispatch,
                    modifier = modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxSize(),
                )
            }
        }

        is UiState.Error -> {
            // Error screen
        }
    }
}

@Composable
fun SettingScreen(
    uiState: SettingUiState,
    onSettingEvent: (SettingEvent) -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        UserProfile(
            userProfile = uiState.userProfile,
            onSettingEvent = onSettingEvent,
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun UserProfile(
    userProfile: UserProfile,
    onSettingEvent: (SettingEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        // Avatar Image
        GlideImage(
            model = if (userProfile is UserProfile.User) userProfile.avatarImageUrl else R.drawable.ic_person_48,
            contentDescription = stringResource(R.string.profile_image_content_description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .border(3.dp, Color.LightGray, CircleShape)
                .background(Gray1),
        )

        // Name
        Text(
            text = if (userProfile is UserProfile.User) userProfile.name else stringResource(R.string.ask_to_login),
            fontSize = 16.sp,
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp),
        )

        // SignIn or SignOut Button
        Button(
            shape = RoundedCornerShape(8.dp),
            onClick = {
                if (userProfile is UserProfile.User) {
                    onSettingEvent(SettingEvent.SignOut(userProfile.sessionId.value))
                } else {
                    onSettingEvent(SettingEvent.SignIn)
                }
            },
            modifier = Modifier
                .padding(start = 20.dp)
        ) {
            Text(
                text = if (userProfile is UserProfile.User) {
                    stringResource(R.string.sign_out)
                } else {
                    stringResource(R.string.sign_in)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSettingScreen(
    @PreviewParameter(SettingUiStateProvider::class) uiState: SettingUiState,
) {
    SettingScreen(
        uiState = uiState,
        onSettingEvent = {},
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
    )
}
