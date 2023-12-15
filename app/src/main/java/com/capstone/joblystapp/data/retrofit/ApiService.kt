package com.capstone.joblystapp.data.retrofit

import com.capstone.joblystapp.data.response.JobDetailResponse
import com.capstone.joblystapp.data.response.LoginResponse
import com.capstone.joblystapp.data.response.PreferredJobsResponse
import com.capstone.joblystapp.data.response.RegisterResponse
import com.capstone.joblystapp.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("/register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @POST("/user")
    fun user(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<UserResponse>

    @GET("/job")
    fun getPreferredJobs(
        @Query("jobCategory") category: String
    ): Call<PreferredJobsResponse>

    @GET("/job")
    fun getJobDetail(
        @Query("jobPosition") jobPosition: String,
        @Query("officeName") officeName: String,
        @Query("city") city: String,
        @Query("salary") salary: String,
        @Query("workTime") workTime: String
    ): Call<JobDetailResponse>
}
