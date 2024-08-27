package com.dcs.presentation.ui.moviedetail.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.dcs.presentation.R
import com.dcs.presentation.core.designsystem.widget.CircularProgressBarWithPercentage

@Composable
internal fun MovieInfo(
    title: String,
    score: Int,
    releaseDate: String,
    country: String,
    runtime: String,
    genres: String,
    tagline: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Title(
            title = title,
            modifier = Modifier.fillMaxWidth()
        )

        spacer()

        Score(
            score = score,
            modifier = Modifier.fillMaxWidth()
        )

        spacer()

        Infomation(
            releaseDate = releaseDate,
            country = country,
            runtime = runtime,
            genres = genres,
            modifier = Modifier.fillMaxWidth()
        )

        spacer()
        
        Tagline(
            tagline = tagline,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
internal fun Title(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}

@Composable
internal fun Score(
    score: Int,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        CircularProgressBarWithPercentage(
            modifier = Modifier,
            percentage = score,
            viewSize = 40.dp
        )

        Text(
            text = stringResource(id = R.string.user_score),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxHeight()
                .align(Alignment.CenterVertically)
                .padding(start = 8.dp),
        )
    }
}

@Composable
internal fun Infomation(
    releaseDate: String,
    country: String,
    runtime: String,
    genres: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = "$releaseDate ($country) . $runtime",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = genres,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
internal fun Tagline(
    tagline: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = tagline,
        color = Color.LightGray,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
    )
}

@Composable
internal fun ColumnScope.spacer(height: Dp = 20.dp) {
    Spacer(modifier = Modifier.height(height))
}
