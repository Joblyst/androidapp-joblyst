package com.capstone.joblystapp.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("loginResult")
	val loginResult: LoginResult?,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class LoginResult(

	@field:SerializedName("name")
	val username: String,

	@field:SerializedName("userId")
	val uid: String,

	@field:SerializedName("token")
	val accessToken: String
)
