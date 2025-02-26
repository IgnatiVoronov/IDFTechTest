package com.example.idftechtest.ui.components.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idftechtest.data.model.User
import com.example.idftechtest.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    private val _usersList = MutableStateFlow<List<User>>(emptyList())
    val usersList: StateFlow<List<User>> get() = _usersList

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
}