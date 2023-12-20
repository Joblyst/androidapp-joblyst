package com.example.job.response

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("uid")
	val uid: String,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)
