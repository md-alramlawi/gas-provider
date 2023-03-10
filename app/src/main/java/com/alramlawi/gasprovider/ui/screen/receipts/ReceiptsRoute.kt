package com.alramlawi.gasprovider.ui.screen.receipts

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alramlawi.gasprovider.ui.BottomNavItem
import com.alramlawi.gasprovider.ui.MainRoute


fun NavGraphBuilder.receiptsRoute(navController: NavController) {
    composable(MainRoute.Receipts) { ReceiptsScreen(navController) }
}
