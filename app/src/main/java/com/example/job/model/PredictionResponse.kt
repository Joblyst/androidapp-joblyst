package com.example.job.model


import com.google.gson.annotations.SerializedName

data class PredictionResponse(
    @SerializedName("prediction")
    val prediction: List<String>
)