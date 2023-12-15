package com.capstone.joblystapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.joblystapp.data.repository.JobRepository
import com.capstone.joblystapp.data.response.Job
import com.capstone.joblystapp.data.response.JobDetailResponse
import com.capstone.joblystapp.data.response.PreferredJobsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JobViewModel(private val jobRepository: JobRepository) : ViewModel() {

    private val _preferredJobs = MutableLiveData<List<Job>>()
    val preferredJobs: LiveData<List<Job>> get() = _preferredJobs

    private val _jobDetail = MutableLiveData<JobDetailResponse>()
    val jobDetail: LiveData<JobDetailResponse> get() = _jobDetail

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchPreferredJobs(jobCategory: String) {
        jobRepository.getPreferredJobs(jobCategory).enqueue(object : Callback<PreferredJobsResponse> {
            override fun onResponse(
                call: Call<PreferredJobsResponse>,
                response: Response<PreferredJobsResponse>
            ) {
                if (response.isSuccessful) {
                    _preferredJobs.value = response.body()?.preferredJobs
                } else {
                    _error.value = "Failed to fetch preferred jobs. Please try again later."
                }
            }

            override fun onFailure(call: Call<PreferredJobsResponse>, t: Throwable) {
                _error.value = "Network error. Please check your internet connection."
            }
        })
    }

    fun fetchJobDetail(job: Job) {
        jobRepository.getJobDetail(job).enqueue(object : Callback<JobDetailResponse> {
            override fun onResponse(call: Call<JobDetailResponse>, response: Response<JobDetailResponse>) {
                if (response.isSuccessful) {
                    _jobDetail.value = response.body()
                } else {
                    _error.value = "Failed to fetch job details. Please try again later."
                }
            }

            override fun onFailure(call: Call<JobDetailResponse>, t: Throwable) {
                _error.value = "Network error. Please check your internet connection."
            }
        })
    }
}
