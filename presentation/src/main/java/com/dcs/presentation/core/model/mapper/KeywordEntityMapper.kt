package com.dcs.presentation.core.model.mapper

import com.dcs.domain.model.Keyword
import com.dcs.presentation.core.model.KeywordUiState

fun Keyword.toUiState() = KeywordUiState(
    keyword = keyword

)
