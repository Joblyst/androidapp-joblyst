package com.example.job.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.job.helper.UserModel
import com.example.job.helper.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel (private val userRepository: UserRepository) : ViewModel() {

    fun login(email: String, password: String) = userRepository.loginUser(email, password)

    fun saveToken(token: UserModel) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.saveToken(token)
        }
    }

    fun login() {
        viewModelScope.launch {
            userRepository.login()
        }
    }
}