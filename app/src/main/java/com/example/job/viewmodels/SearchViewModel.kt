package com.example.job.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.job.helper.UserModel
import com.example.job.helper.UserRepository
import com.example.job.response.GetJobsResponse
import com.example.job.response.JobsItem
import com.example.job.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel (private val userRepository: UserRepository): ViewModel() {
    private val _article = MutableLiveData<List<JobsItem>>()
    val article: LiveData<List<JobsItem>> = _article

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun checkToken(): LiveData<UserModel> {
        return userRepository.getToken()
    }
    init {
        findJobs(TOKEN,NAME)
    }

    fun findJobs(token:String, query :String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearch(token,query)
        client.enqueue(object : Callback<GetJobsResponse> {
            override fun onResponse(
                call: Call<GetJobsResponse>,
                response: Response<GetJobsResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _article.value = response.body()?.jobs
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GetJobsResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object{
        private const val TOKEN = ""
        private const val TAG = "SearchViewModel"
        private const val NAME = ""
    }


}