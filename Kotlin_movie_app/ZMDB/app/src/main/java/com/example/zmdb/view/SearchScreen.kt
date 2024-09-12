package com.example.zmdb.view


import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.zmdb.model.ToWatch
import com.example.zmdb.model.Top
import com.example.zmdb.viewModel.SearchViewModel


/**
 * Gives the user a chance to input a movie title, then displays the data taken from TMDB API
 */
@Composable
fun SearchScreen(navController: NavController, searchViewModel: SearchViewModel = viewModel()) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.search_topbar),
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchViewModel.searchText,
                onValueChange = { searchViewModel.searchText = it },
                label = { Text(stringResource(id = R.string.search_hover)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    searchViewModel.searchMovies()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.search_button), style = MaterialTheme.typography.titleMedium)
            }

            Spacer(modifier = Modifier.height(16.dp))

            searchViewModel.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            // Display search results
            LazyColumn {
                items(searchViewModel.searchResults) { item ->
                    val title = item.getString("title")
                    val posterPath = item.getString("poster_path")

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w500${posterPath}"),
                            contentDescription = title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        Text(text = title, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(onClick = {
                                Top.addToTop(item)
                            }) {
                                Text(stringResource(id = R.string.add_top_button))
                            }
                            Button(onClick = {
                                ToWatch.addToWatchlist(item)
                            }) {
                                Text(stringResource(id = R.string.add_watchlist_button))
                            }
                            Button(onClick = {
                                val movieDataString = Uri.encode(item.toString())
                                navController.navigate("movie_details/$movieDataString")
                            }){
                                Text(stringResource(id = R.string.movie_page_button))
                            }
                        }
                    }
                }
            }
        }
    }
}