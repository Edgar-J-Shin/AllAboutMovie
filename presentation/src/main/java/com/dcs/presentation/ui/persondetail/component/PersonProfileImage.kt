package com.dcs.presentation.ui.persondetail.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.component.BasicImage
import com.dcs.presentation.core.model.PersonDetailUiState
import com.dcs.presentation.core.model.ProfileImageUiState
import com.dcs.presentation.core.model.getProfileImageUrl
import com.dcs.presentation.core.model.getProfileUrl

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
internal fun SharedTransitionScope.PersonProfileImage(
    uiState: PersonDetailUiState,
    isProfileImagesShowing: Boolean,
    onChanged: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedVisibility(
        visible = !isProfileImagesShowing,
        enter = fadeIn() + scaleIn(),
        exit = fadeOut() + scaleOut(),
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .sharedBounds(
                    sharedContentState = rememberSharedContentState(key = "${uiState.name}-bounds"),
                    // Using the scope provided by AnimatedVisibility
                    animatedVisibilityScope = this,
                )
        ) {
            PersonProfileImageContent(
                profileImageUrl = uiState.getProfileUrl(),
                profileImages = uiState.profileImages,
                isProfileImagesShowing = isProfileImagesShowing,
                onClick = onChanged,
                modifier = Modifier
                    .sharedElement(
                        state = rememberSharedContentState(key = uiState.name),
                        animatedVisibilityScope = this@AnimatedVisibility
                    )
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ProfileImageDetails(
    uiState: PersonDetailUiState,
    isProfileImagesShowing: Boolean,
    onChanged: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = isProfileImagesShowing,
        transitionSpec = {
            fadeIn() togetherWith fadeOut()
        },
        label = stringResource(R.string.label_profile_image_details),
    ) { targetState ->
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (targetState) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(onClick = onChanged)
                        .background(Color.Black.copy(alpha = 0.5f))
                )
                Column(
                    modifier = Modifier
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(key = "${uiState.name}-bounds"),
                            animatedVisibilityScope = this@AnimatedContent,
                        )
                ) {
                    PersonProfileImageContent(
                        profileImageUrl = uiState.getProfileUrl(),
                        profileImages = uiState.profileImages,
                        isProfileImagesShowing = isProfileImagesShowing,
                        onClick = onChanged,
                        modifier = Modifier
                            .sharedElement(
                                state = rememberSharedContentState(key = uiState.name),
                                animatedVisibilityScope = this@AnimatedContent
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun PersonProfileImageContent(
    profileImageUrl: String,
    profileImages: List<ProfileImageUiState>,
    isProfileImagesShowing: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
    ) {
        if (isProfileImagesShowing) {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 25.dp),
                horizontalArrangement = Arrangement.spacedBy(25.dp),
                modifier = Modifier.clickable(onClick = onClick)
            ) {
                items(profileImages) { item ->
                    BasicImage(
                        imageUrl = item.getProfileImageUrl(),
                        contentDescription = stringResource(id = R.string.content_description_profile),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(350.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
            }
        } else {
            BasicImage(
                imageUrl = profileImageUrl,
                contentDescription = stringResource(id = R.string.content_description_profile),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = 50.dp)
                    .widthIn(max = 450.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable(onClick = onClick)
            )
        }
    }
}
