package com.example.job.helper

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.job.retrofit.ApiConfig

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("token")

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val preferences = UserPreferences.getInstance(context.dataStore)
        val apiService1 = ApiConfig.getApiService("https://joblyst-api-cpe5hpucwa-uc.a.run.app")
        val apiService2 = ApiConfig.getApiService("https://another-api-url.com")
        return UserRepository.getInstance(apiService1, apiService2, preferences)
    }
}