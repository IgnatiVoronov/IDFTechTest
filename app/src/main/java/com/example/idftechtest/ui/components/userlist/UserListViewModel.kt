package com.example.idftechtest.ui.components.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idftechtest.data.model.User
import com.example.idftechtest.data.model.UserDetails
import com.example.idftechtest.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    private val _usersList = MutableStateFlow<List<User>>(emptyList())
    val usersList: StateFlow<List<User>> get() = _usersList

    private val _userDetails = MutableStateFlow<UserDetails?>(null)
    val userDetails: StateFlow<UserDetails?> get() = _userDetails

    // Get a list of users
    fun getUsers() {
        viewModelScope.launch {
            try {
                val userList = userRepository.getUsers()
                _usersList.value = userList
            } catch (e: Exception) {
                // Error handling
                _usersList.value = emptyList() // Returns an empty sheet if an error occurs
            }
        }
    }

    // Getting user details by ID
    fun getUserDetails(userId: Int) {
        viewModelScope.launch {
            try {
                val userDetail = userRepository.getUserDetails(userId)
                _userDetails.value = userDetail
            } catch (e: Exception) {
                // Error handling
                _userDetails.value = null
            }
        }
    }
}