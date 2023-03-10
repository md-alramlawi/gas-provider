package com.alramlawi.gasprovider.ui.screen.customers

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alramlawi.gasprovider.ui.MainRoute


fun NavGraphBuilder.customersRoute(navController: NavController) {
    composable(MainRoute.Customers) { CustomersScreen(navController) }
}
