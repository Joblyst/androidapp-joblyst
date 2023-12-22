package com.example.job.model


import com.google.gson.annotations.SerializedName

data class JobByPositionResponse(
    @SerializedName("jobs")
    val jobs: List<Job>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
)