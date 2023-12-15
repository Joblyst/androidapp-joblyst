package com.capstone.joblystapp.data.response

import com.google.gson.annotations.SerializedName

data class JobDetailResponse(
    @SerializedName("jobPosition")
    val jobPosition: String,

    @SerializedName("officeName")
    val officeName: String,

    @SerializedName("city")
    val city: String,

    @SerializedName("salary")
    val salary: String,

    @SerializedName("workTime")
    val workTime: String,

    @SerializedName("jobLevel")
    val jobLevel: String,

    @SerializedName("qualification")
    val qualification: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("status")
    val status: String
)
