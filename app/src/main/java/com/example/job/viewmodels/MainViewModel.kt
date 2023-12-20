package com.example.job.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.job.helper.UserModel
import com.example.job.helper.UserRepository

class MainViewModel(private val userRepository: UserRepository): ViewModel() {

    fun checkToken(): LiveData<UserModel> {
        return userRepository.getToken()
    }
}