package com.example.zmdb.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zmdb.BuildConfig
import com.example.zmdb.model.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

/**
 * Retains the informations given by the user and the API.
 * Also calls the API with the data given as an input by the user.
 */
class SearchViewModel : ViewModel() {
    var searchText by mutableStateOf("")
    var searchResults by mutableStateOf(listOf<JSONObject>())
    var errorMessage by mutableStateOf<String?>(null)

    fun searchMovies() {
        viewModelScope.launch {
            val client = Client.getMyClient()
            val request = Request.Builder()
                .url("https://api.themoviedb.org/3/search/movie?query=${searchText}&include_adult=false&language=en-US&page=1")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", BuildConfig.API_KEY)
                .build()

            withContext(Dispatchers.IO) {
                try {
                    val response = client.newCall(request).execute()
                    val responseBody = response.body?.string()
                    if (response.isSuccessful && responseBody != null) {
                        val jsonObject = JSONObject(responseBody)
                        val resultsArray = jsonObject.getJSONArray("results")

                        val resultsList = mutableListOf<JSONObject>()
                        for (i in 0 until resultsArray.length()) {
                            resultsList.add(resultsArray.getJSONObject(i))
                        }
                        searchResults = resultsList
                        errorMessage = null
                    }
                } catch (e: IOException) {
                    errorMessage = "Network error. Please check your connection and try again."
                }
            }
        }
    }
}