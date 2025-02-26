package com.example.idftechtest.ui.navigation

enum class Screen(val route: String) {
    UsersListFragment("users_list"),
    UserDetailsFragment("user_details/{userId}")
}