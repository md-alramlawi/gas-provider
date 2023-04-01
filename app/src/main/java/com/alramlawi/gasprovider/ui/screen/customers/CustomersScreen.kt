package com.alramlawi.gasprovider.ui.screen.customers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.alramlawi.gasprovider.R
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import com.alramlawi.gasprovider.ui.composable.*
import com.alramlawi.gasprovider.ui.screen.add_edit_customer.navigateToAddEditCustomer
import com.alramlawi.gasprovider.ui.screen.customer_data.navigateToCustomerData


@Composable
fun CustomersScreen(
    navController: NavController,
    viewModel: CustomersViewModel = hiltViewModel()
) {

    val listState by viewModel.listState.collectAsState()
    val screenState by viewModel.screenState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Screen(isLoading = screenState.loading) {

        CustomersContent(
            customers = listState,
            editable = screenState.isAdmin,
            onSearch = viewModel::filterCustomers,
            searchQuery = searchQuery,
            onClickItem = { navController.navigateToCustomerData(it) },
            onClickEdit = { navController.navigateToAddEditCustomer(it) },
            onDelete = viewModel::deleteCustomer
        )
    }

}

@Composable
fun CustomersContent(
    customers: List<CustomerEntity>,
    editable: Boolean,
    onSearch: (String) -> Unit,
    searchQuery: String,
    onClickItem: (CustomerEntity) -> Unit,
    onClickEdit: (CustomerEntity) -> Unit,
    onDelete: (CustomerEntity) -> Unit
) {

    Column(Modifier.fillMaxSize()) {

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .padding(horizontal = 8.dp, vertical = 16.dp),
        ) {
            SearchBarItem(
                modifier = Modifier.fillMaxWidth(),
                query = searchQuery,
                placeHolderText = stringResource(id = R.string.search),
                onQueryChanged = onSearch
            )
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {

            items(items = customers, key = { it.id }) {
                DeletableItem(
                    enable = editable,
                    item = it,
                    content = {
                        CustomerItem(
                            editable = true,
                            customerEntity = it,
                            onClickItem = { onClickItem(it) },
                            onClickEdit = { onClickEdit(it) }
                        )
                    },
                    onDelete = onDelete
                )
            }
        }
    }
}




