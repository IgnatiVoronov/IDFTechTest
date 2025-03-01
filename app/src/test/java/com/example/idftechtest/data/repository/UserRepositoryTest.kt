package com.example.idftechtest.data.repository

import com.example.idftechtest.data.database.UserDao
import com.example.idftechtest.data.model.Address
import com.example.idftechtest.data.model.User
import com.example.idftechtest.data.network.ApiService
import com.example.idftechtest.data.util.toEntity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class UserRepositoryTest {
    private lateinit var userRepository: UserRepository
    private lateinit var apiService: ApiService
    private lateinit var userDao: UserDao

    @Before
    fun setup() {
        apiService = mock(ApiService::class.java)
        userDao = mock(UserDao::class.java)
        userRepository = UserRepository(apiService, userDao)
    }

    @Test
    fun `getUsers should return users from API and save to database`() = runBlocking {
        val users = listOf(User(1, "John", "john@example.com", "1234567890", Address("New York")))
        `when`(apiService.getUsers()).thenReturn(users)

        val result = userRepository.getUsers()

        assertEquals(users, result)
        verify(userDao, times(1)).insertUser (users[0].toEntity())
    }

    @Test
    fun `getUsers should return users from local database when API fails`() = runBlocking {
        val localUsers = listOf(User(1, "Jane", "jane@example.com", "0987654321", Address("Los Angeles")))
        `when`(apiService.getUsers()).thenThrow(RuntimeException("API Error"))
        `when`(userDao.getAllUsers()).thenReturn(localUsers.map { it.toEntity() })

        val result = userRepository.getUsers()

        assertEquals(localUsers, result)
    }

    @Test
    fun `getUsers should throw exception when API fails and local database is empty`() = runBlocking {
        `when`(apiService.getUsers()).thenThrow(RuntimeException("API Error"))
        `when`(userDao.getAllUsers()).thenReturn(emptyList())

        val exception = assertThrows(RuntimeException::class.java) {
            runBlocking { userRepository.getUsers() }
        }

        assertEquals("Unable to access the API and no users found in local database.", exception.message)
    }

    @Test
    fun `getUser Details should return user from API`() = runBlocking {
        val userId = 1
        val user = User(1, "John", "john@example.com", "1234567890", Address("New York"))
        `when`(apiService.getUserDetails(userId)).thenReturn(user)

        val result = userRepository.getUserDetails(userId)

        assertEquals(user, result)
    }

    @Test
    fun `getUser Details should return user from local database when API fails`() = runBlocking {
        val userId = 1
        val localUser  = User(1, "Jane", "jane@example.com", "0987654321", Address("Los Angeles"))
        `when`(apiService.getUserDetails(userId)).thenThrow(RuntimeException("API Error"))
        `when`(userDao.getUserById(userId)).thenReturn(localUser.toEntity())

        val result = userRepository.getUserDetails(userId)

        assertEquals(localUser, result)
    }

    @Test
    fun `getUser Details should throw exception when API fails and user not found in local database`() = runBlocking {
        val userId = 1
        `when`(apiService.getUserDetails(userId)).thenThrow(RuntimeException("API Error"))
        `when`(userDao.getUserById(userId)).thenReturn(null)

        val exception = assertThrows(RuntimeException::class.java) {
            runBlocking { userRepository.getUserDetails(userId) }
        }

        assertEquals("Unable to access the API and user not found in local database.", exception.message)
    }
}
