package com.example.job.retrofit

import com.example.job.response.GetJobsResponse
import com.example.job.response.LoginResponse
import com.example.job.response.ProfileResponse
import com.example.job.response.RegisterResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("/register")
    fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("job/random")
    suspend fun getJobs(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<GetJobsResponse>

    @GET("job/search")
    fun getSearch(
        @Header("Authorization") token: String,
        @Query("q") query: String
    ): Call<GetJobsResponse>

    @GET("/user")
    fun getUserByUsername(
        @Header("Authorization") token : String,
    ): Call<ProfileResponse>

}