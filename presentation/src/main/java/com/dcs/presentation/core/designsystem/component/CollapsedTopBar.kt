package com.dcs.presentation.core.designsystem.component

import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsedTopBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    scrollState: LazyListState = rememberLazyListState(),
    statusBarPadding: Boolean = true,
) {

    // TopAppBar의 높이 저장
    var topBarHeight by remember { mutableFloatStateOf(0f) }
    var topBarOffset by remember { mutableFloatStateOf(0f) } // 스크롤에 따른 TopAppBar의 Y축 이동
    var accumulatedScroll by remember { mutableFloatStateOf(0f) } // 스크롤 누적 값

    // 스크롤 상태에 따른 첫 번째 아이템 정보
    val firstVisibleItemIndex by remember {
        derivedStateOf { scrollState.firstVisibleItemIndex }
    }

    val firstVisibleItemOffset by remember {
        derivedStateOf { scrollState.firstVisibleItemScrollOffset }
    }

    // 스크롤 시 TopAppBar가 완전히 사라질 때까지 동작을 제어
    LaunchedEffect(firstVisibleItemOffset, firstVisibleItemIndex) {
        val totalScroll = firstVisibleItemOffset + accumulatedScroll

        topBarOffset = if (firstVisibleItemIndex == 0) {
            // 첫 번째 아이템이 보이는 동안 TopAppBar를 스크롤에 맞춰 점차적으로 숨김
            (-totalScroll).coerceIn(-topBarHeight, 0f)
        } else {
            // 첫 번째 아이템이 사라지면 TopAppBar는 사라진 상태로 유지
            -topBarHeight
        }

        accumulatedScroll = totalScroll
    }

    val statusBarPaddingModifier = if (statusBarPadding) Modifier.statusBarsPadding() else Modifier

    CenterAlignedTopAppBar(
        title = title,
        navigationIcon = navigationIcon,
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                // TopBar의 높이를 저장
                topBarHeight = coordinates.size.height.toFloat()
            }
            .graphicsLayer {
                translationY = topBarOffset // TopAppBar가 스크롤에 따라 사라지고 나타남 (Float 타입 적용)
            }
            .then(statusBarPaddingModifier)
    )
}
