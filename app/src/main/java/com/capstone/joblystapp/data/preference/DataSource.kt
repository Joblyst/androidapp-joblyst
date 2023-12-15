package com.capstone.joblystapp.data.preference

import androidx.lifecycle.LiveData
import com.capstone.joblystapp.data.response.LoginResponse
import com.capstone.joblystapp.data.response.LoginResult
import com.capstone.joblystapp.data.response.RegisterResponse

interface DataSource {
    fun getSession(): LiveData<LoginResult>
    fun login(email: String, password: String): LiveData<LoginResponse>
    fun register(username: String, email: String, password: String): LiveData<RegisterResponse>
}