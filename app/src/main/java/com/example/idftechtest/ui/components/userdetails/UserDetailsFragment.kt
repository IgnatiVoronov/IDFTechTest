package com.example.idftechtest.ui.components.userdetails

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun UserDetailsFragment(userId: String?) {
    val userDetailsViewModel:UserDetailsViewModel = hiltViewModel()
    val userDetails by userDetailsViewModel.userDetails.collectAsState()

    if (userId != null) {
        userDetailsViewModel.getUserDetails(userId.toInt())
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp)
        ) {
            Text(
                text = userDetails.name,
                style = TextStyle(fontSize = 20.sp)
            )
            Text(
                text = userDetails.email,
                style = TextStyle(fontSize = 20.sp)
            )
            Text(
                text = userDetails.phone,
                style = TextStyle(fontSize = 20.sp)
            )
            Text(
                text = userDetails.city,
                style = TextStyle(fontSize = 20.sp)
            )
        }
    }
}