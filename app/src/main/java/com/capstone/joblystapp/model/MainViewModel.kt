package com.capstone.joblystapp.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.joblystapp.data.preference.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository) : ViewModel() {
    fun getSession() = repository.getSession()

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}