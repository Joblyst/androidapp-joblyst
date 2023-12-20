package com.example.job.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetJobsResponse(

	@field:SerializedName("jobs")
	val jobs: List<JobsItem>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

@Parcelize
data class JobsItem(

	@field:SerializedName("country")
	val country: String,

	@field:SerializedName("jobCategory")
	val jobCategory: String,

	@field:SerializedName("officeLogo")
	val officeLogo: String,

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("rangeSalary")
	val rangeSalary: String,

	@field:SerializedName("industry")
	val industry: String,

	@field:SerializedName("office")
	val office: String,

	@field:SerializedName("salary")
	val salary: String,

	@field:SerializedName("workTime")
	val workTime: String,

	@field:SerializedName("jobLevel")
	val jobLevel: String,

	@field:SerializedName("jobPosition")
	val jobPosition: String,

	@field:SerializedName("qualifications")
	val qualifications: List<String>,

	@field:SerializedName("jobLevelSpecific")
	val jobLevelSpecific: String
): Parcelable
