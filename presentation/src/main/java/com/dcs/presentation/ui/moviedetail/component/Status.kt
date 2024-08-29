package com.dcs.presentation.ui.moviedetail.component

import android.icu.text.NumberFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dcs.presentation.R
import java.util.Locale

@Composable
fun Status(
    language: String,
    budget: Int,
    revenue: Int,
    modifier: Modifier = Modifier,
    format: NumberFormat = NumberFormat.getCurrencyInstance(Locale("en-US")),
) {
    if (language.isNotEmpty()) {
        StatusText(
            title = stringResource(id = R.string.movie_detail_original_language_title),
            description = language,
            modifier = modifier
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
    if (budget > 0) {
        StatusText(
            title = stringResource(id = R.string.movie_detail_budget_title),
            description = format.format(budget),
            modifier = modifier
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
    if (revenue > 0) {
        StatusText(
            title = stringResource(id = R.string.movie_detail_revenue_title),
            description = format.format(revenue),
            modifier = modifier
        )
    }
}

@Composable
fun StatusText(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .padding(top = 4.dp)
        )
    }
}
