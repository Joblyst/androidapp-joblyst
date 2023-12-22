package com.example.job.model

import com.google.gson.annotations.SerializedName

data class Job(
    @SerializedName("city")
    val city: String,
    @SerializedName("country")
    val country: String,
    @SerializedName("industry")
    val industry: String,
    @SerializedName("jobCategory")
    val jobCategory: String,
    @SerializedName("jobLevel")
    val jobLevel: String,
    @SerializedName("jobLevelSpecific")
    val jobLevelSpecific: String,
    @SerializedName("jobPosition")
    val jobPosition: String,
    @SerializedName("office")
    val office: String,
    @SerializedName("officeLogo")
    val officeLogo: String,
    @SerializedName("qualifications")
    val qualifications: List<String>,
    @SerializedName("rangeSalary")
    val rangeSalary: String,
    @SerializedName("salary")
    val salary: String,
    @SerializedName("workTime")
    val workTime: String
)