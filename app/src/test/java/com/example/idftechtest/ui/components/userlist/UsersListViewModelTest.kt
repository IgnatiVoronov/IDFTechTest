package com.example.idftechtest.ui.components.userlist

import com.example.idftechtest.data.model.User
import com.example.idftechtest.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

@ExperimentalCoroutinesApi
class UsersListViewModelTest {

    private lateinit var userRepository: UserRepository
    private lateinit var viewModel: UsersListViewModel

    @Before
    fun setUp() {

        Dispatchers.setMain(StandardTestDispatcher())
        userRepository = mock(UserRepository::class.java)
        viewModel = UsersListViewModel(userRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getUsers should update usersList when repository returns data`() = runTest {

        val users = listOf(User(1, "John"), User(2, "Jane"))
        `when`(userRepository.getUsers()).thenReturn(users)

        kotlinx.coroutines.test.runTest { viewModel.getUsers() }

        val result = viewModel.usersList.first()
        assertEquals(users, result)
        verify(userRepository, times(1)).getUsers()
    }

    @Test
    fun `getUsers should update errorMessage when repository throws exception`() = runTest {

        val exception = RuntimeException("Network error")
        `when`(userRepository.getUsers()).thenThrow(exception)

        kotlinx.coroutines.test.runTest { viewModel.getUsers() }

        val result = viewModel.errorMessage.first()
        assertEquals("Data access error: ${exception.message}", result)
        verify(userRepository, times(1)).getUsers()
    }

    @Test
    fun `clearErrorMessage should clear error message`() = runTest {

        val exception = RuntimeException("Network error")
        `when`(userRepository.getUsers()).thenThrow(exception)
        kotlinx.coroutines.test.runTest { viewModel.getUsers() }

        viewModel.clearErrorMessage()

        val result = viewModel.errorMessage.first()
        assertEquals("", result)
    }
}
