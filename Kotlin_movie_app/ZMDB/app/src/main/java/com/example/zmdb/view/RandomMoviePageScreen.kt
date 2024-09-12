package com.example.zmdb.view

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.zmdb.BottomAppBarComponent
import com.example.zmdb.R
import com.example.zmdb.viewModel.RandomMoviePageViewModel

/**
 * Display of the page that offers the user a button to get a random movie
 */
@Composable
fun RandomMoviePageScreen(
    navController: NavController,
    movieData: String?,
    viewModel: RandomMoviePageViewModel = viewModel()
) {
    // Set the movie in the corresponding viewModel
    viewModel.setMovieData(movieData)

    // Done to observe the viewModel to keep the data
    val title by viewModel.title.collectAsState()
    val posterPath by viewModel.posterPath.collectAsState()
    val overview by viewModel.overview.collectAsState()
    val voteAverage by viewModel.voteAverage.collectAsState()
    val error by viewModel.error.collectAsState()

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
                    text = title ?: stringResource(id = R.string.no_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Image(
                    painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${posterPath}"),
                    contentDescription = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Text(
                    text = overview ?: stringResource(id = R.string.no_overview),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Rating: ${voteAverage ?: stringResource(id = R.string.no_rating)}",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Button(
                    onClick = { viewModel.handleWatchMovie(navController) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = stringResource(id = R.string.random_watch), style = MaterialTheme.typography.bodyLarge)
                }
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                Button(
                    onClick = { viewModel.handleGetAnotherRandomMovie(navController) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(Color(0xFF2E0CF3)),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(text = stringResource(id = R.string.random_other_movie), color = Color.Yellow, style = MaterialTheme.typography.bodyLarge)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}