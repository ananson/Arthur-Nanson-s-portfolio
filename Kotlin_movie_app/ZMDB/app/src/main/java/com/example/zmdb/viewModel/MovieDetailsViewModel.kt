package com.example.zmdb.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

/**
 * ViewModel for a movie page, retains the data in case of a change in the state of the view
 */
class MovieDetailsViewModel : ViewModel() {
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title

    private val _posterPath = MutableStateFlow("")
    val posterPath: StateFlow<String> = _posterPath

    private val _overview = MutableStateFlow("")
    val overview: StateFlow<String> = _overview

    private val _voteAverage = MutableStateFlow(0.0)
    val voteAverage: StateFlow<Double> = _voteAverage

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadMovieDetails(movieData: String?) {
        viewModelScope.launch {
            try {
                val jsonObject = JSONObject(Uri.decode(movieData))
                _title.value = jsonObject.getString("title")
                _posterPath.value = jsonObject.getString("poster_path")
                _overview.value = jsonObject.getString("overview")
                _voteAverage.value = jsonObject.getDouble("vote_average")
            } catch (e: Exception) {
                _error.value = "Error loading movie details"
            }
        }
    }
}
