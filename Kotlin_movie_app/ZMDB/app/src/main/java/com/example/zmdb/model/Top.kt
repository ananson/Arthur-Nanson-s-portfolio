package com.example.zmdb.model

import com.example.zmdb.BuildConfig
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.getValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

//Represents the top of the user, used to link with database en store the movies
object Top {
    private var toplist: MutableList<JSONObject> = mutableListOf()

    fun getTop(): List<JSONObject> {
        return toplist.toList()
    }

    //adds a movie to the local top, then updates the database
    fun addToTop(item: JSONObject) {
        val itemId = item.getInt("id")
        val isItemAlreadyInTopList = toplist.any { it.getInt("id") == itemId }

        if (!isItemAlreadyInTopList) {//check if it's not already in, causes crashes otherwise
            toplist.add(item)
            val sanitizeTop = sanitizeTitle(item.getString(("title")))
            this.addToDB(itemId, sanitizeTop)
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


    fun deleteFromTop(item: Int){
        var count = 1
        val iterator = toplist.iterator()
        while (iterator.hasNext()) {
            val jsonObject = iterator.next()
            if (jsonObject.optInt("id") == item) {
                iterator.remove()
                this.removeFromDB(sanitizeTitle(jsonObject.getString("title")))
            }
            count++
        }
    }

    fun swapOrder(index: Int, index2: Int){
        val temp = toplist[index]
        toplist[index] = toplist[index2]
        toplist[index2] = temp
        changeDBOrder(index+1, index2+1)
    }

    //Used to transform the Database into the good format
    fun dbToLocal(list: DataSnapshot){
        val client = Client.getMyClient()
        val requests = mutableListOf<Int>()
        val items = mutableListOf<Item>()

        for (childSnapshot in list.children) {
            try{
                val childData = childSnapshot.getValue<Map<String, Int>>()
                val id = childData?.get("id") ?: -1
                val order = childData?.get("order") ?: -1

                if (id != -1 && order != -1) {
                    items.add(Item(id, order))
                }
            }
            catch(e: Exception) {
                // Log any exceptions for debugging
                e.printStackTrace()
                println("Error processing child snapshot: ${childSnapshot.key}")
            }

        }
        val sortedItems = items.sortedBy { it.order }
        requests.addAll(sortedItems.map { it.id })


        GlobalScope.launch(Dispatchers.IO) {
                // Sequentially process each request
                for (number in requests) {
                    val request = Request.Builder()
                        .url("https://api.themoviedb.org/3/movie/$number?language=en-US")
                        .get()
                        .addHeader("accept", "application/json")
                        .addHeader("Authorization", BuildConfig.API_KEY)
                        .build()

                    try {
                        val response = client.newCall(request).execute()
                        val responseBody = response.body?.string()
                        if (response.isSuccessful) {
                            responseBody?.let {
                                val jsonObject = JSONObject(it)
                                synchronized(toplist) {
                                    toplist.add(jsonObject)
                                }
                            }
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        }



    private fun addToDB(toadd: Int, title: String){

        val database = Client.getMyDatabase()
        val dataMap = mapOf(
            "id" to toadd,
            "order" to this.toplist.size
        )

        database.child(CurrentUser.getCurrentUser()).child("top").child(title).setValue(dataMap)
    }

    //Removes a movie from the database, first gets it's top position then deletes it
    private fun removeFromDB(toRemove: String){
        val database = Client.getMyDatabase()
        val ref = database.child(CurrentUser.getCurrentUser()).child("top").child(toRemove)
        ref.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snapshot = task.result
                if (snapshot != null) {

                    try {
                        val childData = snapshot.getValue<Map<String, Int>>()
                        val index = childData?.get("order")

                        if (index != null) {
                            changeOrderAfterRemove(index)
                        }
                    }
                    catch(e: Exception) {
                        e.printStackTrace()
                        println("Error processing child snapshot")
                    }

                }
            }
        }
        ref.removeValue()
    }


    //As a movie is deleted, we need to update the position of the other movies in the top
    private fun changeOrderAfterRemove(index : Int){
        val database = Client.getMyDatabase()
        val ref = database.child(CurrentUser.getCurrentUser()).child("top")
        ref.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snapshot = task.result
                if (snapshot != null) {

                    try{
                        val updates = mutableMapOf<String, Int>()

                        //For every child, if the position is greater than the one we deleted, we move it up one rank
                        for (child in snapshot.children) {
                            println("this is child $child")
                            val childData = child.getValue<Map<String, Int>>()
                            var order = childData?.get("order")
                            if (order != null) {
                                if(order>index){
                                    order -= 1
                                    val childKey = child.key
                                    updates["$childKey/order"] = order
                                }
                            }

                        }
                        ref.updateChildren(updates as Map<String, Any>)
                    }
                    catch(e: Exception) {
                        e.printStackTrace()
                        println("Error processing child snapshot")
                    }

                }
            }
        }
    }

    //Swap the order of two movies in the database
    private fun changeDBOrder(index1 : Int, index2: Int) {
        val database = Client.getMyDatabase()
        val topRef = database.child(CurrentUser.getCurrentUser()).child("top")

        topRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snapshot = task.result
                if (snapshot != null) {
                    val updates = mutableMapOf<String, Int>()
                    var item1Key: String? = null
                    var item2Key: String? = null

                    try{
                        for (child in snapshot.children) {
                            val childData = child.getValue<Map<String, Int>>()
                            val id = childData?.get("id") ?: -1
                            val order = childData?.get("order") ?: -1
                            if (id != -1 && order != -1) {
                                if (order == index1) {
                                    item1Key = child.key
                                } else if (order == index2) {
                                    item2Key = child.key
                                }
                            }
                        }
                        if (item1Key != null && item2Key != null) {
                            //swap order
                            updates["$item1Key/order"] = index2
                            updates["$item2Key/order"] = index1
                            //update database
                            topRef.updateChildren(updates as Map<String, Any>)
                        }

                    }
                    catch(e: Exception) {
                        e.printStackTrace()
                        println("Error processing child snapshot")
                    }

                }
            }
        }
    }

}
//Used to get Order and then sort by order
data class Item(val id: Int, val order: Int)


