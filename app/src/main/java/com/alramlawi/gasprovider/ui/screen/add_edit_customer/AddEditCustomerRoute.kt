package com.alramlawi.gasprovider.ui.screen.add_edit_customer

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import com.alramlawi.gasprovider.ui.MainRoute
import com.alramlawi.gasprovider.ui.screen.IdArgs

fun NavController.navigateToAddEditCustomer(customer: CustomerEntity?) {
    navigate("${MainRoute.AddEditCustomer}?${IdArgs.ITEM_ID}=${customer?.id}")
}

fun NavGraphBuilder.addEditCustomerRoute(navController: NavController) {
    composable(
        route = "${MainRoute.AddEditCustomer}?${IdArgs.ITEM_ID}={${IdArgs.ITEM_ID}}",
        arguments = listOf(
            navArgument(name = IdArgs.ITEM_ID) { NavType.StringType }
        )
    ) {
        AddEditCustomerScreen(navController)
    }
}