package com.fjr619.themovie_kmm.android.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fjr619.themovie_kmm.domain.entity.Movie

@Composable
fun MoviePlaySection(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String = "",
    movies: List<Movie>,
    onItemClicked: (Movie) -> Unit
) {
    val gridState = rememberLazyListState()

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.SemiBold,
        )
        if (subtitle.isEmpty().not()) {
            Text(
                text = subtitle, modifier = Modifier

                    .padding(start = 16.dp), color = MaterialTheme.colorScheme.onSurface.copy(0.5f),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Left
            )
        }
        LazyRow(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            state = gridState,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp)
        ) {
            itemsIndexed(movies) { _, item ->
                MovieBannerItem(
                    modifier = Modifier.clickable {
                        onItemClicked.invoke(item)
                    },
                    posterUrl = item.poster_path,
                    title = item.title,
                )
            }
        }
    }
}