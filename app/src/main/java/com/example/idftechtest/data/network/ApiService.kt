package com.example.idftechtest.data.network

import com.example.idftechtest.data.model.User
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    // Get a list of users
    @GET("users")
    suspend fun getUsers(): List<User>

    // Get user details
    @GET("users/{id}")
    suspend fun getUserDetails(@Path("id") userId: Int): User
}