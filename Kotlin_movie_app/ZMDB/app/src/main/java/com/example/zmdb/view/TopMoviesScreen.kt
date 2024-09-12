package com.example.zmdb.view

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.zmdb.BottomAppBarComponent
import com.example.zmdb.R
import com.example.zmdb.viewModel.TopMoviesViewModel
import org.json.JSONObject
/**
 * Displays the current user's top. They then have the choice to delete it, go to the movie page
 * or to rearrange it's position in the top
 */
@Composable
fun TopMoviesScreen(navController: NavController = rememberNavController(), viewModel: TopMoviesViewModel = viewModel()) {
    val topMovies by remember { derivedStateOf { viewModel.topMovies } }

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.top_topbar),
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        },
        bottomBar = {
            BottomAppBarComponent(navController)
        },
        content = { paddingValues ->
            viewModel.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(topMovies, key = { it.getInt("id") }) { movie ->
                    MovieItem(
                        movie = movie,
                        navController = navController,
                        onRemove = { movieId ->
                            viewModel.removeMovie(movieId)
                        },
                        onMoveUp = { movieId ->
                            viewModel.moveMovie(movieId, -1)
                        },
                        onMoveDown = { movieId ->
                            viewModel.moveMovie(movieId, 1)
                        }
                    )
                }
            }
        }
    )
}

/**
 * A movie to display
 */
@Composable
fun MovieItem(
    movie: JSONObject,
    navController: NavController,
    onRemove: (Int) -> Unit,
    onMoveUp: (Int) -> Unit,
    onMoveDown: (Int) -> Unit
) {
    val title = movie.getString("title")
    val posterPath = movie.getString("poster_path")
    val id = movie.getInt("id")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500$posterPath"),
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
        )


        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 4.dp),
            color = MaterialTheme.colorScheme.primary
        )

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                onMoveUp(id)
            }) {
                Text(stringResource(id = R.string.up))
            }
            Button(onClick = {
                onMoveDown(id)
            }) {
                Text(stringResource(id = R.string.down))
            }
            Button(onClick = {
                onRemove(id)
            }) {
                Text(stringResource(id = R.string.delete_button), style = MaterialTheme.typography.bodyLarge)
            }
            Button(onClick = {
                val movieDataString = Uri.encode(movie.toString())
                navController.navigate("movie_details/$movieDataString")
            }) {
                Text(stringResource(id = R.string.movie_page_button), style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
