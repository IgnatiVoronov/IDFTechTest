package com.example.idftechtest

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.idftechtest.ui.navigation.NavigationGraph

@Composable
fun MyApp() {
    val navController = rememberNavController()
    NavigationGraph(navController)
}