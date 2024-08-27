package com.dcs.presentation.ui.moviedetail.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Crews(
    crews: List<Pair<String, String>>,
    modifier: Modifier = Modifier,
) {
    (0..1).forEach { rowNum ->
        Row(
            modifier = modifier
        ) {
            (0..1).forEach { colNum ->
                val index = rowNum * 2 + colNum
                if (crews.size <= index) return@Row

                StatusText(
                    title = crews[index].first,
                    description = crews[index].second,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            }
        }

        if (rowNum == 0) {
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
