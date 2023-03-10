package com.alramlawi.gasprovider.ui.screen.customer_data

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import com.alramlawi.gasprovider.ui.MainRoute
import com.alramlawi.gasprovider.ui.screen.IdArgs

fun NavController.navigateToCustomerData(customer: CustomerEntity?) {
    navigate("${MainRoute.CustomerData}?${IdArgs.ITEM_ID}=${customer?.id}")
}

fun NavGraphBuilder.customerDataRoute(navController: NavController) {
    composable(
        route = "${MainRoute.CustomerData}?${IdArgs.ITEM_ID}={${IdArgs.ITEM_ID}}",
        arguments = listOf(
            navArgument(name = IdArgs.ITEM_ID) { NavType.StringType }
        )
    ) {
        CustomerDataScreen(navController)
    }
}