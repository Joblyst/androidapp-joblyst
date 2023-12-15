package com.capstone.joblystapp.di

import android.content.Context
import com.capstone.joblystapp.data.preference.UserPreference
import com.capstone.joblystapp.data.preference.UserRepository
import com.capstone.joblystapp.data.preference.dataStore
import com.capstone.joblystapp.data.response.RemoteData
import com.capstone.joblystapp.data.retrofit.ApiConfig

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiService()
        val userPreferenceDatastore = UserPreference.getInstance(context.dataStore)
        val remoteDataSource = RemoteData.getInstance()
        return UserRepository.getInstance(apiService, userPreferenceDatastore, remoteDataSource)
    }
}