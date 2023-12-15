package com.capstone.joblystapp.data.response

import com.google.gson.annotations.SerializedName

data class PreferredJobsResponse(

	@field:SerializedName("preferredJobs")
	val preferredJobs: List<Job>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class Job(
	@SerializedName("jobPosition")
	val jobPosition: String,

	@SerializedName("officeName")
	val officeName: String,

	@SerializedName("city")
	val city: String,

	@SerializedName("salary")
	val salary: String,

	@SerializedName("workTime")
	val workTime: String
)
