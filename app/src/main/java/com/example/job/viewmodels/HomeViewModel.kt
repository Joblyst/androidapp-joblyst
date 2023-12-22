package com.example.job.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.job.helper.UserModel
import com.example.job.helper.UserRepository
import com.example.job.response.GetJobsResponse
import com.example.job.response.JobsItem
import com.example.job.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel (
    private val userRepository: UserRepository
): ViewModel() {

    val getListArticle: LiveData<PagingData<JobsItem>> =
        userRepository.getJobs().cachedIn(viewModelScope)

}