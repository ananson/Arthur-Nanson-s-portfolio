package com.example.zmdb.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zmdb.model.ToWatch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.random.Random

/**
 * Loads the watchlist and gets a random movie from it once the user presses the button
 */
class RandomScreenViewModel : ViewModel() {
    private val _showEmptyMessage = MutableStateFlow(false)
    val showEmptyMessage: StateFlow<Boolean> = _showEmptyMessage

    private val _toWatch = MutableStateFlow<List<JSONObject>>(emptyList())

    init {
        loadWatchlist()
    }

    private fun loadWatchlist() {
        viewModelScope.launch {
            _toWatch.value = ToWatch.getWatchlist()
        }
    }

    /**
     * Get a random movie
     */
    fun getRandomMovie(): String? {
        val list = _toWatch.value
        return if (list.isEmpty()) {
            _showEmptyMessage.value = true
            null
        } else {
            _showEmptyMessage.value = false
            val randomMovie = list[Random.nextInt(list.size)]
            Uri.encode(randomMovie.toString())
        }
    }
}
