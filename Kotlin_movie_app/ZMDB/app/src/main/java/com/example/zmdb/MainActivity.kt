package com.example.zmdb


//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.zmdb.view.LoginScreen
import com.example.zmdb.view.MovieDetailsScreen
import com.example.zmdb.view.RandomMoviePageScreen
import com.example.zmdb.view.RandomScreen
import com.example.zmdb.view.SearchScreen
import com.example.zmdb.view.TopMoviesScreen
import com.example.zmdb.view.WatchListScreen

/**
 * Main activity, calls the entire app
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZMDBTheme {
                ZMDB()
            }
        }
    }
}

/**
 * Theme for the app
 */
@Composable
fun ZMDBTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFFFFEB3B),
            onPrimary = Color(0xFF000000),
            primaryContainer = Color(0xFFFFF176),
            onPrimaryContainer = Color(0xFF000000),
            secondary = Color(0xFF000000),
            onSecondary = Color(0xFFFFEB3B),
            background = Color(0xFF000000),
            onBackground = Color(0xFFFFEB3B),
            surface = Color(0xFF000000),
            onSurface = Color(0xFFFFEB3B)
        )
        ,
        typography = Typography(
            displayLarge = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 34.sp,
                letterSpacing = 0.25.sp,
                lineHeight = 40.sp
            ),
            displayMedium = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                letterSpacing = 0.15.sp,
                lineHeight = 30.sp
            ),
            displaySmall = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                letterSpacing = 0.1.sp,
                lineHeight = 26.sp
            ),
            headlineLarge = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                letterSpacing = 0.sp,
                lineHeight = 28.sp
            ),
            headlineMedium = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp,
                letterSpacing = 0.sp,
                lineHeight = 26.sp
            ),
            headlineSmall = TextStyle(
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                letterSpacing = 0.sp,
                lineHeight = 24.sp
            ),
            bodyLarge = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                letterSpacing = 0.5.sp,
                lineHeight = 22.sp
            ),
            bodyMedium = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                letterSpacing = 0.25.sp,
                lineHeight = 20.sp
            ),
            bodySmall = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                letterSpacing = 0.4.sp,
                lineHeight = 18.sp
            ),
            labelLarge = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                letterSpacing = 0.1.sp,
                lineHeight = 20.sp
            ),
            labelMedium = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                letterSpacing = 0.1.sp,
                lineHeight = 16.sp
            ),
            labelSmall = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 10.sp,
                letterSpacing = 0.2.sp,
                lineHeight = 14.sp
            )
        ),
        shapes = Shapes(
            small = RoundedCornerShape(4.dp),
            medium = RoundedCornerShape(8.dp),
            large = RoundedCornerShape(16.dp)
        )
    ) {
        content()
    }
}

/**
 * Main app, declares the navigation and sets the first destination
 */
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ZMDB() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute != "login") {
                BottomAppBarComponent(navController)
            }
        }
    ) {
        NavHost(navController, startDestination = "login") {
            composable("login") { LoginScreen(navController) }
            composable("top") { TopMoviesScreen(navController) }
            composable("watchlist") { WatchListScreen(navController) }
            composable("search") { SearchScreen(navController) }
            composable("random") { RandomScreen(navController) }
            composable("movie_details/{movieData}") { backStackEntry ->
                val movieData = backStackEntry.arguments?.getString("movieData")
                MovieDetailsScreen(navController, movieData)
            }
            composable("random_movie_page/{movieData}") { backStackEntry ->
                val movieData = backStackEntry.arguments?.getString("movieData")
                RandomMoviePageScreen(navController, movieData)
            }
        }
    }

}

/**
 * Navigation bar at the bottom of the screen
 */
@Composable
fun BottomAppBarComponent(navController: NavController) {
    BottomAppBar(
        backgroundColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,

    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            BottomNavigationItem(
                icon = { Icon(Icons.Filled.Favorite, contentDescription = "Top") },
                label = { Text("Top") },
                selected = false,
                onClick = {
                    navController.navigate("top")
                }
            )
            BottomNavigationItem(
                icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Watchlist") },
                label = { Text("Watchlist") },
                selected = false,
                onClick = {
                    navController.navigate("watchlist")
                }
            )
            BottomNavigationItem(
                icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                label = { Text("Search") },
                selected = false,
                onClick = {
                    navController.navigate("search")
                }
            )
            BottomNavigationItem(
                icon = { Icon(Icons.AutoMirrored.Filled.HelpOutline, contentDescription = "Random") },
                label = { Text("Random") },
                selected = false,
                onClick = {
                    navController.navigate("random")
                }
            )
        }
    }
}