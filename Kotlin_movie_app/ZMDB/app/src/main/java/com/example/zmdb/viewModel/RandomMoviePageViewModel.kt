package com.example.zmdb.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.zmdb.model.ToWatch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.random.Random

/**
 * Retains the data for a movie given randomly, also gives the choice to get another random movie
 */
class RandomMoviePageViewModel : ViewModel() {
    private val _title = MutableStateFlow<String?>("")
    val title: StateFlow<String?> = _title

    private val _posterPath = MutableStateFlow<String?>("")
    val posterPath: StateFlow<String?> = _posterPath

    private val _overview = MutableStateFlow<String?>("")
    val overview: StateFlow<String?> = _overview

    private val _voteAverage = MutableStateFlow<Double?>(0.0)
    val voteAverage: StateFlow<Double?> = _voteAverage

    private val _error = MutableStateFlow<String?>("")
    val error: StateFlow<String?> = _error

    private val _movieId = MutableStateFlow<Int?>(0)
    private val movieId: StateFlow<Int?> = _movieId

    fun setMovieData(movieDataString: String?) {
        _error.value = null
        val jsonObject = movieDataString?.let {
            try {
                JSONObject(it)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }

        if (jsonObject != null) {
            _title.value = jsonObject.getString("title")
            _posterPath.value = jsonObject.getString("poster_path")
            _overview.value = jsonObject.getString("overview")
            _voteAverage.value = jsonObject.getDouble("vote_average")
            _movieId.value = jsonObject.getInt("id")
        } else {
            _error.value = "Error loading movie details"
        }
    }

    /**
     * Deletes from watchlist and redirects towards it
     */
    fun handleWatchMovie(navController: NavController) {
        if (movieId.value != null) {
            viewModelScope.launch {
                ToWatch.deleteToWatchList(movieId.value!!)
                navController.navigate("watchlist") {
                    popUpTo("random_movie_page") { inclusive = true }
                }
            }
        }
    }

    /**
     * Get another random movie
     */
    fun handleGetAnotherRandomMovie(navController: NavController) {
        viewModelScope.launch {
            val toWatch = ToWatch.getWatchlist()
            if (toWatch.isNotEmpty()) {
                val randomMovie = toWatch[Random.nextInt(toWatch.size)]
                val movieDataString = Uri.encode(randomMovie.toString())
                navController.navigate("random_movie_page/$movieDataString")
            }
        }
    }
}