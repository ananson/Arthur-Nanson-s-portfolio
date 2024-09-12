package com.example.zmdb.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zmdb.model.Top
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.IOException

/**
 * Loads the user's top movies and enables the user to move a movie's position and to delete it from their top
 */
class TopMoviesViewModel : ViewModel() {
    private val _topMovies = mutableStateListOf<JSONObject>()
    val topMovies: List<JSONObject> get() = _topMovies
    var errorMessage by mutableStateOf<String?>(null)

    init {
        try{
            loadTopMovies()
        }
        catch (e: IOException) {
            errorMessage = "Network error. Please check your connection and try again."
        }
    }

    private fun loadTopMovies() {
        viewModelScope.launch {
            try{
                _topMovies.clear()
                _topMovies.addAll(Top.getTop())
                errorMessage = null
            }
            catch (e: IOException) {
                errorMessage = "Network error. Please check your connection and try again."
            }
        }
    }

    fun removeMovie(movieId: Int) {
        try{
            _topMovies.removeAll { it.getInt("id") == movieId }
            Top.deleteFromTop(movieId)
            errorMessage = null
        }

        catch (e: IOException) {
            errorMessage = "Network error. Please check your connection and try again."
        }
    }

    fun moveMovie(movieId: Int, direction: Int) {
        val index = _topMovies.indexOfFirst { it.getInt("id") == movieId }
        if (index != -1 && (index + direction in _topMovies.indices)) {//don't go out of bounds
            try{
                val newIndex = (index + direction).coerceIn(0, _topMovies.size - 1)
                val movie = _topMovies.removeAt(index)
                _topMovies.add(newIndex, movie)
                Top.swapOrder(index, index + direction)
                errorMessage = null
            }
            catch (e: IOException) {
                errorMessage = "Network error. Please check your connection and try again."
            }
        }
    }
}
