package com.example.job.helper

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.job.response.JobsItem
import com.example.job.retrofit.ApiService
import kotlinx.coroutines.flow.first

class JobsPagingSource (private val userPreferences: UserPreferences, private val apiService: ApiService) : PagingSource<Int, JobsItem>() {

    override fun getRefreshKey(state: PagingState<Int, JobsItem>): Int? {
        return state.anchorPosition?.let {
            val anchorPage = state.closestPageToPosition(it)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, JobsItem> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val token = userPreferences.getToken().first().token

            if (token.isNotEmpty()) {
                val responseData = apiService.getJobs(token, position, params.loadSize)
                if (responseData.isSuccessful) {
                    Log.d("Article Paging Source", "Load: ${responseData.body()}")
                    LoadResult.Page(
                        data = responseData.body()?.jobs ?: emptyList(),
                        prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                        nextKey = if (responseData.body()?.jobs.isNullOrEmpty()) null else position + 1
                    )
                } else {
                    Log.d("Token", "Load Error: $token")
                    LoadResult.Error(Exception("Failed"))
                }
            } else {
                LoadResult.Error(Exception("Failed"))
            }
        } catch (e: Exception) {
            Log.d("Exception", "Load Error: ${e.message}")
            return LoadResult.Error(e)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }
}