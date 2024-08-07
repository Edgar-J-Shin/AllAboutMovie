package com.dcs.presentation.ui.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.dcs.presentation.core.designsystem.widget.LoadingDialog
import com.dcs.presentation.core.extensions.collectAsEffect
import com.dcs.presentation.core.model.SettingUiState
import com.dcs.presentation.core.model.SettingUiStateProvider
import com.dcs.presentation.core.model.UserProfile
import com.dcs.presentation.core.model.toDisplayName
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
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    viewModel.effect.collectAsEffect { effect ->
        when (effect) {
            is SettingEffect.SignIn -> {
                navController.navigate(Screen.SignIn.createRoute(effect.requestToken.value))
            }

            is SettingEffect.ShowSnackbar -> {
                snackBarHostState.showSnackbar(
                    message = context.getString(effect.state.messageResId),
                    duration = effect.state.duration,
                )
            }
        }
    }

    SettingScreen(
        state = state,
        onSettingEvent = viewModel::dispatchEvent,
        isLoading = isLoading,
        snackbarHostState = snackBarHostState,
        modifier = modifier
            .fillMaxSize(),
    )
}

@Composable
fun SettingScreen(
    state: UiState<SettingUiState>,
    onSettingEvent: (SettingUiEvent) -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    Box(
        modifier = modifier,
    ) {
        when (state) {
            is UiState.Loading -> {
                // Loading screen
                Skeleton(
                    modifier = modifier
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                        .fillMaxWidth()
                )
            }

            is UiState.Success -> {
                val settingUiState = state.data
                if (settingUiState != null) {
                    SettingContents(
                        uiState = settingUiState,
                        onSettingEvent = onSettingEvent,
                        modifier = Modifier
                            .padding(horizontal = 20.dp, vertical = 20.dp)
                            .fillMaxSize(),
                    )

                    if (isLoading) {
                        LoadingDialog()
                    }
                }
            }

            is UiState.Error -> {
                // Error screen
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}

@Composable
private fun SettingContents(
    uiState: SettingUiState,
    onSettingEvent: (SettingUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
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
    onSettingEvent: (SettingUiEvent) -> Unit,
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
            text = userProfile.toDisplayName(),
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
                    onSettingEvent(SettingUiEvent.SignOut(userProfile.sessionId))
                } else {
                    onSettingEvent(SettingUiEvent.SignIn)
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

@Composable
private fun Skeleton(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Avatar Image
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .border(3.dp, Color.LightGray, CircleShape)
                    .background(Gray1),
            )

            // Name
            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .background(Gray1)
                    .size(30.dp)
                    .weight(1f),
            )

            // SignIn or SignOut Button
            Box(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .width(80.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Gray1),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingScreenPreview(
    @PreviewParameter(SettingUiStateProvider::class) state: Pair<UiState<SettingUiState>, Boolean>,
) {
    SettingScreen(
        state = state.first,
        isLoading = state.second,
        onSettingEvent = {},
    )
}
