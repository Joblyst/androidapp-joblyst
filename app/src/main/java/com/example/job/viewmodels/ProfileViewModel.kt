package com.example.job.viewmodels

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.*
import androidx.lifecycle.viewModelScope
import com.example.job.helper.UserModel
import com.example.job.helper.UserRepository
import com.example.job.response.ProfileResponse
import com.example.job.response.User
import com.example.job.retrofit.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(private val userRepository: UserRepository): ViewModel() {
    private val _dataProfile = MutableLiveData<User>()
    val dataProfile: LiveData<User> = _dataProfile

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.deleteToken()
        }
    }
    fun checkToken(): LiveData<UserModel> {
        return userRepository.getToken()
    }

    fun getUser(): LiveData<UserModel> {
        return userRepository.getUser().asLiveData()
    }

    fun getDataUser(token: String) {
        val client = ApiConfig.getApiService("https://joblyst-api-cpe5hpucwa-uc.a.run.app").getUserByUsername(token)
        client.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {

                if (response.isSuccessful) {
                    val responseData = response.body()?.user
                    if (responseData != null) {
                        _dataProfile.value = responseData as User
                    } else {
                        Log.e(ContentValues.TAG, "Response data is null")
                    }
                }
                else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}