package com.alramlawi.gasprovider.ui.screen.add_edit_receipt

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import com.alramlawi.gasprovider.ui.MainRoute
import com.alramlawi.gasprovider.ui.screen.IdArgs

fun NavController.navigateToAddEditReceipt(receipt: ReceiptEntity?) {
    navigate("${MainRoute.AddEditReceipt}?${IdArgs.ITEM_ID}=${receipt?.id}")
}

fun NavGraphBuilder.addEditReceiptRoute(navController: NavController) {
    composable(
        route = "${MainRoute.AddEditReceipt}?${IdArgs.ITEM_ID}={${IdArgs.ITEM_ID}}",
        arguments = listOf(
            navArgument(name = IdArgs.ITEM_ID) { NavType.StringType }
        )
    ) {
        AddEditReceiptScreen(navController)
    }
}