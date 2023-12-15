package com.capstone.joblystapp.data.preference

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.capstone.joblystapp.data.response.LoginResponse
import com.capstone.joblystapp.data.response.LoginResult
import com.capstone.joblystapp.data.response.RegisterResponse
import com.capstone.joblystapp.data.response.RemoteData
import com.capstone.joblystapp.data.retrofit.ApiService

class UserRepository(
    private val apiService: ApiService,
    private val preference: UserPreference,
    private val remoteData: RemoteData,
) : DataSource {

    override  fun getSession(): LiveData<LoginResult> {
        return preference.getSession().asLiveData()
    }
    suspend fun saveSession(name: String, userId: String, token: String) {
        preference.saveSession(name, userId, token)
    }
    suspend fun logout() {
        preference.logout()
    }

    override fun login(email: String, password: String): LiveData<LoginResponse> {
        val inResponse = MutableLiveData<LoginResponse>()
        remoteData.login(object : RemoteData.LoginCallback{
            override fun onLogin(loginResponse: LoginResponse) {
                inResponse.postValue(loginResponse)
            }
        } ,email, password)
        return inResponse
    }

    override fun register(
        username: String,
        email: String,
        password: String
    ): LiveData<RegisterResponse> {
        val signupResponse = MutableLiveData<RegisterResponse>()

        remoteData.signup(object : RemoteData.RegisterCallback{
            override fun onRegister(registerResponse: RegisterResponse) {
                signupResponse.postValue(registerResponse)
            }
        }, username, email, password)
        return signupResponse
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            pref: UserPreference,
            remoteDataSource: RemoteData,
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService, pref, remoteDataSource)
            }.also { instance = it }
    }
}