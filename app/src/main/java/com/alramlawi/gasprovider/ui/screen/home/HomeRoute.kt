package com.alramlawi.gasprovider.ui.screen.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alramlawi.gasprovider.ui.BottomNavItem
import com.alramlawi.gasprovider.ui.MainRoute

fun NavGraphBuilder.homeRoute(navController: NavController) {
    composable(MainRoute.Home) { HomeScreen(navController) }
}
