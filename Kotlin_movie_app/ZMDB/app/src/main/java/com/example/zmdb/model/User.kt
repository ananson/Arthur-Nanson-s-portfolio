package com.example.zmdb.model
//Database format, we have a top and a watchlist
data class User(var top : MutableList<Int>, var watchlist : MutableList<Int>)

