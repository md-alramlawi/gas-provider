package com.alramlawi.gasprovider.ui.screen.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alramlawi.gasprovider.R
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import com.alramlawi.gasprovider.ui.composable.*
import com.alramlawi.gasprovider.ui.screen.add_edit_customer.navigateToAddEditCustomer
import com.alramlawi.gasprovider.ui.screen.add_edit_receipt.navigateToAddEditReceipt


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    val screenState by viewModel.screenState.collectAsState()

    val scaffoldState = rememberScaffoldState()

    Screen(isLoading = screenState.loading) {

        Scaffold(
            floatingActionButton = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    FloatingActionButton(
                        onClick = { navController.navigateToAddEditCustomer(null) },
                        backgroundColor = MaterialTheme.colors.primary
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_person),
                            contentDescription = "",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }

                    FloatingActionButton(
                        onClick = { navController.navigateToAddEditReceipt(null) },
                        backgroundColor = MaterialTheme.colors.primaryVariant
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_receipt),
                            contentDescription = "",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }

            },
            scaffoldState = scaffoldState
        ) { padding ->

            ReceiptsContent(
                modifier = Modifier.padding(padding),
                receipts = state.receipts,
                totalAmount = state.totalAmount,
                totalCash = state.totalCash,
                totalRemaining = state.totalRemaining,
                editable = screenState.isAdmin,
                onClickEdit = { navController.navigateToAddEditReceipt(it) },
                onDelete = viewModel::deleteReceipt
            )
        }
    }
}

@Composable
fun ReceiptsContent(
    modifier: Modifier = Modifier,
    receipts: List<ReceiptEntity>,
    totalAmount: Double,
    totalCash: Double,
    totalRemaining: Double,
    editable: Boolean,
    onClickEdit: (ReceiptEntity) -> Unit,
    onDelete: (ReceiptEntity) -> Unit
) {

    Column(Modifier.fillMaxWidth()) {
        TodayStatisticsSection(
            totalAmount = totalAmount,
            totalCash = totalCash,
            totalRemaining = totalRemaining
        )

        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (receipts.isEmpty()) {
                EmptyItem()
            }
            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(horizontal = 8.dp)) {

                items(receipts) {
                    DeletableItem(
                        enable = editable,
                        item = it,
                        content = {
                            ReceiptItem(
                                receiptEntity = it,
                                editable = editable,
                                onEdit = { onClickEdit(it) }
                            )
                        },
                        onDelete = onDelete
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(200.dp))
                }
            }
        }
    }
}





