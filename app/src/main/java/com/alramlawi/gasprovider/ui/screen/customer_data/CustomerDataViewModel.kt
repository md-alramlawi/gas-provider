package com.alramlawi.gasprovider.ui.screen.customer_data

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import com.alramlawi.gasprovider.data.repository.receipt.ReceiptRepository
import com.alramlawi.gasprovider.ui.screen.IdArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerDataViewModel @Inject constructor(
    private val receiptRepository: ReceiptRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val args: IdArgs = IdArgs(savedStateHandle)

    val state: StateFlow<State> = receiptRepository.receiptStream.map { list ->
        val ownList = list.filter { it.customerId == args.id }
        State(
            receipts = ownList,
            totalAmount = ownList.sumOf { it.amount },
            totalCash = ownList.sumOf { it.cashPayment },
            totalRemaining = ownList.sumOf { it.remainingPayment },
            loading = false
        )

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), State())


    fun deleteReceipt(receipt: ReceiptEntity){
        viewModelScope.launch {
            receiptRepository.deleteReceipt(receipt.id)
        }
    }

    data class State(
        val receipts: List<ReceiptEntity> = emptyList(),
        val totalAmount: Double = 0.0,
        val totalCash: Double = 0.0,
        val totalRemaining: Double = 0.0,
        val isAdmin: Boolean = true,
        val loading: Boolean = true
    )
}