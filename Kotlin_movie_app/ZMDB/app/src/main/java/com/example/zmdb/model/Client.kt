package com.example.zmdb.model

import com.example.zmdb.BuildConfig
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import okhttp3.OkHttpClient
//Singleton to get the client and the database path
object Client {

    private val client = OkHttpClient()
    private val database = FirebaseDatabase.getInstance(BuildConfig.DATABASE_URL).getReference("Users")

    fun getMyClient(): OkHttpClient{
        return this.client
    }
    fun getMyDatabase(): DatabaseReference{
        return this.database
    }
}
