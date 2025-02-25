package com.example.idftechtest.data.repository

import com.example.idftechtest.data.model.User
import com.example.idftechtest.data.model.UserDetails
import com.example.idftechtest.data.network.ApiService

class UserRepository(private val apiService: ApiService) {
    // Get a list of users
    suspend fun getUsers(): List<User> {
        return apiService.getUsers()
    }

    // Get user details
    suspend fun getUserDetails(userId: Int): UserDetails {
        return apiService.getUserDetails(userId)
    }
}