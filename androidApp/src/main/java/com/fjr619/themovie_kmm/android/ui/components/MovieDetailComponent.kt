package com.fjr619.themovie_kmm.android.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.fjr619.themovie_kmm.domain.entity.Movie

@Composable
fun MovieDetailComponent(movie: Movie) {
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (img, poster, title, date, desc) = createRefs()
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.poster_path)
                .scale(Scale.FIT)
                .build(),
            modifier = Modifier
                .constrainAs(poster) {
                    top.linkTo(img.bottom)
                    bottom.linkTo(img.bottom)
                    start.linkTo(parent.start)
                }
                .padding(start = 16.dp, bottom = 16.dp)
                .width(80.dp)
                .height(135.dp)
                .clip(RoundedCornerShape(6.dp)),
            contentDescription = null,
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.Crop,
        )
        Text(
            text = movie.title,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 4.dp)
                .constrainAs(title) {
                    top.linkTo(img.bottom)
                    start.linkTo(poster.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.SemiBold,
        )
        Text(
            text = "Popularity is ${movie.popularity}",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .constrainAs(date) {
                    top.linkTo(title.bottom)
                    start.linkTo(poster.end)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Left,
        )
        Text(
            text = movie.overview,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .constrainAs(desc) {
                    top.linkTo(poster.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Left,
        )
    }
}