package com.example.job.response

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("user")
	val user: User,

	@field:SerializedName("status")
	val status: String
)
data class User(

	@field:SerializedName("preferredJobs")
	val preferredJobs: List<String>,

	@field:SerializedName("photoProfile")
	val photoProfile: String,

	@field:SerializedName("address")
	val address: String,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String,

	@field:SerializedName("fullName")
	val fullName: String,

	@field:SerializedName("fullname")
	val fullname: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)
