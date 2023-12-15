package com.capstone.joblystapp.data.response

import androidx.lifecycle.MutableLiveData
import com.capstone.joblystapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteData {
    val error = MutableLiveData("")
    var responsecode = ""

    fun login(callback: LoginCallback, email: String, password: String) {
        callback.onLogin(
            LoginResponse(
                null,
                true,
                ""
            )
        )

        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                if(response.isSuccessful){
                    response.body()?.let { callback.onLogin(it) }
                }else {
                    when (response.code()) {
                        200 -> responsecode = "200"
                        400 -> responsecode = "400"
                        401 -> responsecode = "401"
                        else -> error.postValue("ERROR ${response.code()} : ${response.message()}")
                    }
                    callback.onLogin(
                        LoginResponse(
                            null,
                            true,
                            responsecode
                        )
                    )
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                callback.onLogin(
                    LoginResponse(
                        null,
                        true,
                        t.message.toString()
                    )
                )
            }
        })
    }

    fun signup(callback: RegisterCallback, name: String, email: String, password: String){
        val registerinfo = RegisterResponse(
            "",
            "",
            ""
        )
        callback.onRegister(
            registerinfo
        )
        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if(response.isSuccessful){
                    response.body()?.let { callback.onRegister(it) }
                    responsecode = "201"
                    callback.onRegister(
                        RegisterResponse(
                            "",
                            "",
                            responsecode
                        )
                    )
                }else {
                    responsecode = "400"
                    callback.onRegister(
                        RegisterResponse(
                            "",
                            "",
                            responsecode
                        )
                    )
                }
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                callback.onRegister(
                    RegisterResponse(
                        "",
                        "",
                        t.message.toString()
                    )
                )
            }
        })
    }

    interface LoginCallback{
        fun onLogin(loginResponse: LoginResponse)
    }
    interface RegisterCallback{
        fun onRegister(registerResponse: RegisterResponse)
    }

    companion object {
        @Volatile
        private var instance: RemoteData? = null

        fun getInstance(): RemoteData =
            instance ?: synchronized(this) {
                instance ?: RemoteData()
            }
    }
}