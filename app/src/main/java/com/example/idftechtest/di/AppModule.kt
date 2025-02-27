package com.example.idftechtest.di

import android.content.Context
import com.example.idftechtest.data.database.AppDatabase
import com.example.idftechtest.data.database.UserDao
import com.example.idftechtest.data.network.ApiService
import com.example.idftechtest.data.network.RetrofitClient
import com.example.idftechtest.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Singleton
    @Provides
    fun provideApiService(): ApiService {
        return RetrofitClient.apiService
    }

    @Singleton
    @Provides
    fun provideUserRepository(apiService: ApiService, userDao: UserDao): UserRepository {
        return UserRepository(apiService, userDao)
    }
}