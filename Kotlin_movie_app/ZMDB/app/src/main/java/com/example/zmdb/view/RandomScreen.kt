package com.example.zmdb.view

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.zmdb.BottomAppBarComponent
import com.example.zmdb.R
import com.example.zmdb.viewModel.RandomScreenViewModel

/**
 * The detail screen of a movie given with the random generator
 * The user can decide to get another one or to watch it. In the later case, the movie is removed from the watchlist
 */
@Composable
fun RandomScreen(
    navController: NavController,
    viewModel: RandomScreenViewModel = viewModel()
) {
    val showEmptyMessage by viewModel.showEmptyMessage.collectAsState()

    Scaffold(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        backgroundColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.random),
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.fillMaxWidth(),
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
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.dice_icon),
                contentDescription = "Dice",
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 16.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val movieDataString = viewModel.getRandomMovie()
                    if (movieDataString != null) {
                        navController.navigate("random_movie_page/$movieDataString")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.random_button))
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (showEmptyMessage) {
                Text(text = stringResource(id = R.string.random_empty), color = MaterialTheme.colorScheme.error)
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}