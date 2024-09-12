package com.example.zmdb.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zmdb.model.ToWatch
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.IOException

/**
 * Loads the user's watchlist and enables them to remove a movie
 */
class WatchListViewModel : ViewModel() {
    private val _watchList = mutableStateListOf<JSONObject>()
    val watchList: List<JSONObject> get() = _watchList
    var errorMessage by mutableStateOf<String?>(null)

    init {
        try {
            loadWatchList()
        }
        catch (e: IOException) {
            errorMessage = "Network error. Please check your connection and try again."
        }
    }

    private fun loadWatchList() {
        viewModelScope.launch {
            try{
                _watchList.clear()
                _watchList.addAll(ToWatch.getWatchlist())
            }
            catch (e: IOException) {
                errorMessage = "Network error. Please check your connection and try again."
            }
        }
    }

    fun removeMovie(movie: JSONObject) {
        viewModelScope.launch {
            try{
                val id = movie.getInt("id")
                ToWatch.deleteToWatchList(id)
                _watchList.remove(movie)
            }
            catch (e: IOException) {
                errorMessage = "Network error. Please check your connection and try again."
            }
        }
    }
}