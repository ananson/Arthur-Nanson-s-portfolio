package com.example.zmdb.view

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.zmdb.BottomAppBarComponent
import com.example.zmdb.R
import com.example.zmdb.model.ToWatch
import com.example.zmdb.model.Top
import com.example.zmdb.viewModel.MovieDetailsViewModel
import org.json.JSONObject

/**
 * Displays the details of a movie given as an argument
 */
@Composable
fun MovieDetailsScreen(
    navController: NavController,
    movieData: String?,
    viewModel: MovieDetailsViewModel = viewModel()
) {
    val title by viewModel.title.collectAsState()
    val posterPath by viewModel.posterPath.collectAsState()
    val overview by viewModel.overview.collectAsState()
    val voteAverage by viewModel.voteAverage.collectAsState()
    val error by viewModel.error.collectAsState()

    // Load when first launched
    LaunchedEffect(movieData) {
        viewModel.loadMovieDetails(movieData)
    }

    if (!error.isNullOrEmpty()) {
        Text(text = error ?: "", color = Color.Red, modifier = Modifier.padding(16.dp))
        return
    }

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        bottomBar = { BottomAppBarComponent(navController) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                val painter: Painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${posterPath}")
                Image(
                    painter = painter,
                    contentDescription = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .padding(bottom = 16.dp)
                )
            }

            item {
                Text(
                    text = overview,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Justify,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Text(
                    text = "${stringResource(id = R.string.rating)} $voteAverage",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {

                    Button(onClick = { Top.addToTop(JSONObject(movieData ?: "{}")) }) {
                        Text(stringResource(id = R.string.add_top_button))
                    }
                    Button(onClick = { ToWatch.addToWatchlist(JSONObject(movieData ?: "{}")) }) {
                        Text(stringResource(id = R.string.add_watchlist_button))
                    }
                    Button(onClick = { navController.popBackStack() }) {
                        Text(stringResource(id = R.string.back))
                    }
                }
            }
        }
    }
}