package com.example.zmdb.model

import org.json.JSONObject


object CurrentUser {
    private lateinit var currentUser: String
    private lateinit var nextToWatch: JSONObject

    fun getCurrentUser(): String {
        return currentUser
    }

    fun setCurrentUser(user: String){
        this.currentUser = user
    }

    fun getBestList(): List<Int>{

        val list = mutableListOf<Int>()
        list.add(238)
        list.add(278)
        list.add(550)
        list.add(1955)
        list.add(62)
        list.add(157336)
        list.add(510)
        list.add(185)
        list.add(807)
        list.add(73)
        return list
    }

    fun setNextToWatch(next: JSONObject){
        this.nextToWatch = next
    }

    fun getNextToWatch(): JSONObject{
        return this.nextToWatch
    }

}
