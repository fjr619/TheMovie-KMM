package com.fjr619.themovie_kmm.android.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fjr619.themovie_kmm.android.ui.components.MovieDetailComponent
import com.fjr619.themovie_kmm.android.ui.components.MoviePlaySection
import com.fjr619.themovie_kmm.domain.entity.Movie
import com.fjr619.themovie_kmm.presentation.feature.movielist.MovieListState
import com.fjr619.themovie_kmm.presentation.feature.movielist.MovieListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieScreen(viewModel: MovieListViewModel = koinViewModel()) {
    val moviesAsync by viewModel.movies.collectAsState()
    val selectedMovie by viewModel.selectedMovie.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .statusBarsPadding(),
    ) {
        val modifier = Modifier.padding(it)
        when (val async = moviesAsync) {
            is MovieListState.Error -> ErrorScreen(modifier) {
                viewModel.loadMovies()
            }

            is MovieListState.Success -> ContentScreen(
                modifier,
                movies = async.data,
                selectedMovie = selectedMovie
            ) { movie ->
                viewModel.selectMovie(movie)
            }

            else -> LoadingScreen(modifier)
        }
    }
}

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier, onTryAgain: () -> Unit
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(vertical = 32.dp, horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = ":(",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Black,
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Text(
            text = "Cannot proceed your request, please try again!"
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Button(onClick = onTryAgain) {
            Text(text = "Try again")
        }
    }
}

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .padding(vertical = 32.dp, horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Spacer(
            modifier = Modifier.height(16.dp)
        )
        Text(
            text = "Loading ...."
        )
    }

}


@Composable
fun ContentScreen(
    modifier: Modifier = Modifier, movies: List<Movie>,
    selectedMovie: Movie,
    onMovieSelected: (Movie) -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(24.dp)) {
        MoviePlaySection(
            title = "Now Playing",
            subtitle = "Watch your favorites movie of the year",
            movies = movies,
        ) {
            onMovieSelected.invoke(it)
        }
        Text(
            text = "Select movie poster to see detail",
            modifier = Modifier.padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.SemiBold,
        )
        if (selectedMovie.id != 0L) {
            MovieDetailComponent(movie = selectedMovie)
        }
    }
}