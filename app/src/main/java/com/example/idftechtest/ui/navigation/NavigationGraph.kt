package com.example.idftechtest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.idftechtest.ui.components.userdetails.UserDetailsFragment
import com.example.idftechtest.ui.components.userlist.UsersListFragment

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.UsersListFragment.route) {
        composable(Screen.UsersListFragment.route) { UsersListFragment(navController = navController) }
        composable(Screen.UserDetailsFragment.route) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId")
            UserDetailsFragment(userId)
        }
    }
}