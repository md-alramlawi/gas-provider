package com.alramlawi.gasprovider.ui

import androidx.annotation.DrawableRes
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.alramlawi.gasprovider.R
import com.alramlawi.gasprovider.ui.screen.add_edit_customer.addEditCustomerRoute
import com.alramlawi.gasprovider.ui.screen.add_edit_receipt.addEditReceiptRoute
import com.alramlawi.gasprovider.ui.screen.customer_data.customerDataRoute
import com.alramlawi.gasprovider.ui.screen.customers.customersRoute
import com.alramlawi.gasprovider.ui.screen.home.homeRoute
import com.alramlawi.gasprovider.ui.screen.receipts.receiptsRoute
import com.alramlawi.gasprovider.ui.screen.setting.settingRoute

@Composable
fun NavigationGraph(
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = MainRoute.Home,
        modifier = modifier
    ) {
        homeRoute(navController)
        receiptsRoute(navController)
        customersRoute(navController)
        settingRoute(navController)
        addEditCustomerRoute(navController)
        addEditReceiptRoute(navController)
        customerDataRoute(navController)
    }
}

object MainRoute {
    const val Home = "home"
    const val Receipts = "receipts"
    const val Customers = "customers"
    const val Setting = "setting"
    const val AddEditCustomer = "add_edit_customer"
    const val AddEditReceipt = "add_edit_receipt"
    const val CustomerData = "customer_data"
}


data class BottomNavItem(
    val titleRes: Int,
    @DrawableRes val icon: Int,
    val route: String
)


@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem(R.string.nave_home_title, R.drawable.ic_home, MainRoute.Home),
        BottomNavItem(R.string.nave_receipts_title, R.drawable.ic_receipt, MainRoute.Receipts),
        BottomNavItem(R.string.nave_customers_title, R.drawable.ic_people, MainRoute.Customers),
        BottomNavItem(R.string.nave_setting_title, R.drawable.ic_setting, MainRoute.Setting),
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    if (items.map { it.route }.contains(currentRoute)) {

        BottomNavigationBarContent(
            items = items,
            currentRoute = currentRoute,
            onItemClick = { item ->
                navController.navigate(item.route) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            })

    }
}


@Composable
fun BottomNavigationBarContent(
    items: List<BottomNavItem>,
    currentRoute: String?,
    onItemClick: (BottomNavItem) -> Unit
) {

    BottomNavigation(
        contentColor = MaterialTheme.colors.onSurface,
        backgroundColor = MaterialTheme.colors.surface
    ) {

        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = stringResource(item.titleRes),
                    )
                },
                label = {
                    Text(stringResource(item.titleRes))
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.primary.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = { onItemClick(item) }
            )
        }
    }
}


@Preview
@Composable
fun PreviewBottomNavigationBarContent() {
    BottomNavigationBarContent(items = listOf(
        BottomNavItem(R.string.nave_home_title, R.drawable.ic_home, MainRoute.Home),
        BottomNavItem(R.string.nave_receipts_title, R.drawable.ic_receipt, MainRoute.Receipts),
        BottomNavItem(R.string.nave_customers_title, R.drawable.ic_people, MainRoute.Customers),
        BottomNavItem(R.string.nave_setting_title, R.drawable.ic_setting, MainRoute.Setting),
    ), currentRoute = MainRoute.Home, onItemClick = {})
}