package com.example.idftechtest.ui.components.userlist

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@Composable
fun UsersListFragment(navController: NavController) {
    val usersListViewModel: UsersListViewModel = hiltViewModel()
    val usersList by usersListViewModel.usersList.collectAsState()
    val errorMessage by usersListViewModel.errorMessage.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        usersListViewModel.getUsers()
    }

    if (errorMessage.isNotBlank()) {
        Toast.makeText(
            context,
            errorMessage,
            Toast.LENGTH_SHORT
        ).show()
        usersListViewModel.clearErrorMessage()
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(40.dp)
        ) {
            // Show friends list on the screen
            usersList.forEach { user ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .clickable {
                            navController.navigate("user_details/${user.id}")
                        },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = user.id.toString(),
                        style = TextStyle(fontSize = 10.sp),
                        modifier = Modifier
                            .weight(0.1f)
                            .padding(start = 3.dp)
                    )
                    Text(
                        text = user.name,
                        style = TextStyle(fontSize = 10.sp),
                        modifier = Modifier
                            .weight(0.4f)
                            .padding(start = 3.dp)
                    )
                    Text(
                        text = user.email,
                        style = TextStyle(fontSize = 10.sp),
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(start = 3.dp)
                    )
                }
            }
        }
    }
}
