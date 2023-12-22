package com.example.job.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.job.helper.ResultState
import com.example.job.helper.UserModel
import com.example.job.helper.UserRepository
import com.example.job.model.Job
import com.example.job.model.JobByPositionResponse
import com.example.job.model.PredictionRequest
import com.example.job.model.PredictionResponse
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

    private val _predictions = MutableLiveData<List<String>>()
    val predictions: LiveData<List<String>> = _predictions


    fun findJobs(token:String, query :String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService("https://joblyst-api-cpe5hpucwa-uc.a.run.app").getSearch(token,query)
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

    fun findPredictions(text: String) {
        _isLoading.value = true
        val request = PredictionRequest(text)
        val client = ApiConfig.getApiService("https://joblyst-ml-api-cpe5hpucwa-uc.a.run.app").predictText(request)
        client.enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(
                call: Call<PredictionResponse>,
                response: Response<PredictionResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _predictions.value = response.body()?.prediction
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })

    }

    fun getJobByPosition(token: String, jobPosition: String): LiveData<ResultState<Job>> {
        val liveData = MutableLiveData<ResultState<Job>>()
        userRepository.getJobByPosition(token, jobPosition).observeForever { result ->
            when (result) {
                is ResultState.Success -> {
                    val job = result.data.jobs.firstOrNull()  // Get the first job from the list
                    job?.let {
                        liveData.value = ResultState.Success(it)
                    } ?: run {
                        liveData.value = ResultState.Error("Job not found")
                    }
                }
                is ResultState.Error -> liveData.value = ResultState.Error(result.error)
                ResultState.Loading -> liveData.value = ResultState.Loading
            }
        }
        return liveData
    }



}