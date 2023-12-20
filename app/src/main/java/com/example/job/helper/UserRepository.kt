package com.example.job.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.asLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.job.response.JobsItem
import com.example.job.response.LoginResponse
import com.example.job.response.RegisterResponse
import com.example.job.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
){
    private val registerResult = MediatorLiveData<ResultState<RegisterResponse>>()
    private val loginResult = MediatorLiveData<ResultState<LoginResponse>>()

    fun registerUser(name: String, email: String, password: String): LiveData<ResultState<RegisterResponse>> {
        registerResult.value = ResultState.Loading
        val client = apiService.register(name, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val registerStatus = response.body()
                    if (registerStatus != null) {
                        registerResult.value = ResultState.Success(registerStatus)
                    } else {
                        registerResult.value = ResultState.Error(error_register)
                    }
                } else {
                    registerResult.value = ResultState.Error(error_register)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                registerResult.value = ResultState.Error(error_register)
            }

        })

        return registerResult
    }

    fun loginUser(email: String, password: String): LiveData<ResultState<LoginResponse>> {
        loginResult.value = ResultState.Loading
        val client = apiService.login(
            email,
            password
        )

        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginInfo = response.body()
                    if (loginInfo != null) {
                        loginResult.value = ResultState.Success(loginInfo)
                    } else {
                        loginResult.value = ResultState.Error(error_login)
                    }
                } else {
                    loginResult.value = ResultState.Error(error_login)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                loginResult.value = ResultState.Error(error_login)
            }
        })

        return loginResult
    }

    fun getJobs(): LiveData<PagingData<JobsItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                JobsPagingSource(userPreferences, apiService)
            }
        ).liveData
    }

    suspend fun saveToken(token: UserModel) {
        userPreferences.saveToken(token)
    }

    fun getToken(): LiveData<UserModel> {
        return userPreferences.getToken().asLiveData()
    }

    suspend fun login() {
        userPreferences.login()
    }

    suspend fun deleteToken() {
        userPreferences.deleteToken()
    }

    fun getUser(): Flow<UserModel> {
        return userPreferences.getToken()
    }


    companion object{
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(apiService: ApiService, userPreferences: UserPreferences) =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService,userPreferences)
            }.also { instance = it }

        private const val error_register = "Failed!, Make sure the e-mail is correct"
        private const val error_login = "Failed, Make sure the e-mail & password is correct!"
    }
}