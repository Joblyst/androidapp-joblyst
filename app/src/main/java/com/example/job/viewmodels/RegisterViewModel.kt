package com.example.job.viewmodels

import androidx.lifecycle.ViewModel
import com.example.job.helper.UserRepository

class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun register(name: String, email: String, password: String) =
        userRepository.registerUser(name, email, password)

}