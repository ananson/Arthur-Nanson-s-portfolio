package com.example.zmdb.view

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.zmdb.BottomAppBarComponent
import com.example.zmdb.R
import com.example.zmdb.model.Top
import com.example.zmdb.viewModel.WatchListViewModel
import org.json.JSONObject

/**
 * Displays the movies in the user's watchlist. They then have the choice to watch it, go to the movie's page
 * or to add it to their top
 */
@Composable
fun WatchListScreen(navController: NavController, viewModel: WatchListViewModel = viewModel()) {
    val watchList by remember { mutableStateOf(viewModel.watchList) }
    val listState = rememberLazyListState()    // Keep scroll

    Scaffold(
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.watchlist_topbar),
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                backgroundColor = MaterialTheme.colorScheme.primary
            )
        },
        bottomBar = { BottomAppBarComponent(navController) }
    ) { paddingValues ->
        viewModel.errorMessage?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        LazyColumn(
            state = listState, // Keep scroll position
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(watchList) { movie ->
                WatchListItem(
                    movie = movie,
                    navController = navController,
                    onDelete = {
                        viewModel.removeMovie(movie)
                    }
                )
            }
        }
    }
}

/**
 * A watchlist movie
 */
@Composable
fun WatchListItem(
    movie: JSONObject,
    navController: NavController,
    onDelete: (Int) -> Unit
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
                Top.addToTop(movie)
            }) {
                Text(stringResource(id = R.string.add_top_button))
            }
            Button(onClick = {
                onDelete(id)
            }) {
                Text(stringResource(id = R.string.delete_button))
            }
            Button(onClick = {
                val movieDataString = Uri.encode(movie.toString())
                navController.navigate("movie_details/$movieDataString")
            }) {
                Text(stringResource(id = R.string.movie_page_button))
            }
        }
    }
}
