package com.alramlawi.gasprovider.ui.screen.setting

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.alramlawi.gasprovider.ui.MainRoute

fun NavGraphBuilder.settingRoute(navController: NavController) {
    composable(MainRoute.Setting) { SettingScreen(navController) }
}
