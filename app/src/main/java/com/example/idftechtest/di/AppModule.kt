package com.example.idftechtest.di

import com.example.idftechtest.data.network.ApiService
import com.example.idftechtest.data.network.RetrofitClient
import com.example.idftechtest.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideUserRepository(apiService: ApiService): UserRepository {
        return UserRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return RetrofitClient.apiService
    }
}