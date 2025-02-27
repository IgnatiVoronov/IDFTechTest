package com.example.idftechtest.data.repository

import com.example.idftechtest.data.database.UserDao
import com.example.idftechtest.data.model.User
import com.example.idftechtest.data.network.ApiService
import com.example.idftechtest.data.util.toEntity
import com.example.idftechtest.data.util.toUser


class UserRepository(private val apiService: ApiService, private val userDao: UserDao) {
    // Get a list of users
    suspend fun getUsers(): List<User> {
        return try {
            val users = apiService.getUsers()
            // Save users in local database
            users.forEach { user ->
                userDao.insertUser(user.toEntity()) // Convert User to UserEntity and save
            }
            users
        } catch (e: Exception) {
            // If an error occurs, get users from the local database
            val localUsers = userDao.getAllUsers().map { it.toUser() }
            if (localUsers.isNotEmpty()) {
                throw Exception("Unable to access the API and no users found in local database.")
            }
            localUsers
        }
    }

    // Get user details
    suspend fun getUserDetails(userId: Int): User {
        return try {
            apiService.getUserDetails(userId)
        } catch (e: Exception) {
            // If an error occurs, get user from the local database
            val localUser = userDao.getUserById(userId)
            localUser?.toUser() ?: throw Exception("Unable to access the API and user not found in local database.")
        }
    }
}