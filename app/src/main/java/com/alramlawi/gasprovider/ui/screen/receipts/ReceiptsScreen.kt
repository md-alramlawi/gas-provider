package com.alramlawi.gasprovider.ui.screen.receipts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import com.alramlawi.gasprovider.ui.composable.*
import com.alramlawi.gasprovider.ui.screen.add_edit_receipt.navigateToAddEditReceipt


@Composable
fun ReceiptsScreen(
    navController: NavController,
    viewModel: ReceiptsViewModel = hiltViewModel()
) {

    val listState by viewModel.listState.collectAsState()
    val screenState by viewModel.screenState.collectAsState()

    Screen(isLoading = screenState.loading) {
        ReceiptsContent(
            customers = listState,
            editable = screenState.isAdmin,
            onClickEdit = { navController.navigateToAddEditReceipt(it) },
            onDelete = viewModel::deleteReceipt
        )
    }

}

@Composable
fun ReceiptsContent(
    customers: List<ReceiptEntity>,
    editable: Boolean,
    onClickEdit: (ReceiptEntity) -> Unit,
    onDelete: (ReceiptEntity) -> Unit
) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (customers.isEmpty()) {
            EmptyItem()
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {

            items(customers) {
                DeletableItem(
                    enable = editable,
                    item = it,
                    content = {
                        ReceiptItem(
                            receiptEntity = it,
                            editable = true,
                            onEdit = { onClickEdit(it) }
                        )
                    },
                    onDelete = onDelete
                )
            }
        }
    }
}




