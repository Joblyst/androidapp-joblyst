package com.capstone.joblystapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.joblystapp.data.preference.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UserRepository) : ViewModel() {

    fun saveSession(username: String, uid: String, accessToken: String) {
        viewModelScope.launch {
            repository.saveSession(username, uid, accessToken)
        }
    }
    fun getSession() = repository.getSession()

    fun registerSession(username: String, email: String, password: String) = repository.register(username, email, password)

    fun loginSession(email: String, password: String) = repository.login(email, password)
}