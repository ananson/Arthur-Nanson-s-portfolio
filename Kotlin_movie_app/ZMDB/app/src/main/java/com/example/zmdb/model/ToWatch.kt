package com.example.zmdb.model

import com.example.zmdb.BuildConfig
import com.google.firebase.database.DataSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

//Represents the watchlist of the user, used to link with database en store the movies
object ToWatch {
    private var watchlist: MutableList<JSONObject> = mutableListOf()

    fun getWatchlist(): List<JSONObject> {
        return watchlist.toList()
    }

    fun addToWatchlist(item: JSONObject) {

        val itemId = item.getInt("id")
        val isItemAlreadyInWatchList = watchlist.any { it.getInt("id") == itemId }

        if (!isItemAlreadyInWatchList) {//check if it's already in before insertion.
            watchlist.add(item)
            this.addToDB(itemId, sanitizeTitle(item.getString("title")))
        }

    }

    //Some characters are not allowed in the firebase database
    private fun sanitizeTitle(title: String): String {
        return title
            .replace(".", "_")
            .replace("#", "_")
            .replace("$", "_")
            .replace("[", "_")
            .replace("]", "_")
    }

    fun deleteToWatchList(item: Int){
        var count = 1
        val iterator = watchlist.iterator()
        while (iterator.hasNext()) {
            val jsonObject = iterator.next()
            if (jsonObject.optInt("id") == item) {
                iterator.remove()
                this.removeFromDB(sanitizeTitle(jsonObject.getString("title")))
            }
            count++
        }
    }

    //Database and local are not in the same format, this function makes sure they are now
    fun dbToLocal(list: DataSnapshot){
        val client = Client.getMyClient()


        for(i in list.children){
            val number = i.getValue(Int::class.java)
            if(number != -1){
                val request = Request.Builder()
                    .url("https://api.themoviedb.org/3/movie/${number}?language=en-US")
                    .get()
                    .addHeader("accept", "application/json")
                    .addHeader("Authorization", BuildConfig.API_KEY)
                    .build()
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val response = client.newCall(request).execute()
                        val responseBody = response.body?.string()
                        if(response.isSuccessful){
                            responseBody?.let {
                                val jsonObject = JSONObject(it)
                                watchlist.add(jsonObject)
                            }
                        }

                    } catch (e: IOException) {

                        e.printStackTrace()
                    }
                }

            }

        }
    }

    private fun addToDB(toadd: Int, title: String){

        val database = Client.getMyDatabase()
        database.child(CurrentUser.getCurrentUser()).child("watchlist").child(title).setValue(toadd)
    }

    private fun removeFromDB(toRemove: String){
        val database = Client.getMyDatabase()
        database.child(CurrentUser.getCurrentUser()).child("watchlist").child(toRemove).removeValue()
    }
}
