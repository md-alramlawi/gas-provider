package com.alramlawi.gasprovider.ui.screen.customer_data

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import com.alramlawi.gasprovider.ui.composable.*
import com.alramlawi.gasprovider.ui.screen.add_edit_receipt.navigateToAddEditReceipt

@Composable
fun CustomerDataScreen(
    navController: NavController,
    viewModel: CustomerDataViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()

    if (state.loading)
        LoadingItem()
    else

        CustomerDataContent(
            receipts = state.receipts,
            totalAmount = state.totalAmount,
            totalCash = state.totalCash,
            totalRemaining = state.totalRemaining,
            editable = state.isAdmin,
            onClickEdit = { navController.navigateToAddEditReceipt(it) },
            onDelete = viewModel::deleteReceipt
        )

}


@Composable
fun CustomerDataContent(
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

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (receipts.isEmpty()) {
                EmptyItem()
            }
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {

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
            }
        }
    }
}