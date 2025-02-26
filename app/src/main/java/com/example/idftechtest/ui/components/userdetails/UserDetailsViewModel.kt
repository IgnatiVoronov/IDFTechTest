package com.example.idftechtest.ui.components.userdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idftechtest.data.model.UserDetails
import com.example.idftechtest.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {
    private val _userDetails = MutableStateFlow(UserDetails())
    val userDetails: StateFlow<UserDetails> get() = _userDetails

    // Getting user details by ID
    fun getUserDetails(userId: Int) {
        viewModelScope.launch {
            try {
                val userDetail = userRepository.getUserDetails(userId)
                _userDetails.value = userDetail
            } catch (e: Exception) {
                // Error handling
            }
        }
    }
}
