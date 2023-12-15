package com.capstone.joblystapp.data.repository

import com.capstone.joblystapp.data.response.Job
import com.capstone.joblystapp.data.response.JobDetailResponse
import com.capstone.joblystapp.data.response.PreferredJobsResponse
import com.capstone.joblystapp.data.retrofit.ApiService
import retrofit2.Call

class JobRepository(private val apiService: ApiService) {

    fun getPreferredJobs(jobCategory: String): Call<PreferredJobsResponse> {
        return apiService.getPreferredJobs(jobCategory)
    }

    fun getJobDetail(job: Job): Call<JobDetailResponse> {
        return apiService.getJobDetail(
            job.jobPosition,
            job.officeName,
            job.city,
            job.salary,
            job.workTime
        )
    }
}