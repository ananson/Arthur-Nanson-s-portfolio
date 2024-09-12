package com.example.zmdb.viewModel

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.zmdb.model.Client
import com.example.zmdb.model.CurrentUser
import com.example.zmdb.model.ToWatch
import com.example.zmdb.model.Top
import com.example.zmdb.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val database = Client.getMyDatabase()
    private val _email = MutableStateFlow(TextFieldValue(""))
    val email: StateFlow<TextFieldValue> = _email

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun onEmailChange(newValue: TextFieldValue) {
        _email.value = newValue
        _errorMessage.value = ""
    }

    /**
     * Checks if the username is valid and if it's already in the database. If it's in we load the data, else we
     * create a new user with that username and redirect to the search screen
     */
    fun onContinue(navController: NavController) {
        if (_email.value.text.isNotEmpty()) {
            viewModelScope.launch {
                database.child(_email.value.text).get().addOnSuccessListener {
                    if (it.exists()) {
                        Top.dbToLocal(it.child("top"))
                        ToWatch.dbToLocal(it.child("watchlist"))
                        CurrentUser.setCurrentUser(_email.value.text)
                        navController.navigate("search")
                    } else {
                        val newTop: MutableList<Int> = mutableListOf()
                        val newWatch: MutableList<Int> = mutableListOf(-1)
                        val user = User(newTop, newWatch)
                        database.child(_email.value.text).setValue(user).addOnSuccessListener {
                            CurrentUser.setCurrentUser(_email.value.text)
                            navController.navigate("search")
                        }
                    }
                }
                .addOnFailureListener {
                    _errorMessage.value = "Can't connect to database. Check your internet connection and try again."
                }
            }

        } else {
            _errorMessage.value = "Please enter a valid username."
        }
    }
}