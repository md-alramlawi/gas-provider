package com.alramlawi.gasprovider.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import com.alramlawi.gasprovider.data.repository.receipt.ReceiptRepository
import com.alramlawi.gasprovider.utils.days
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val receiptRepository: ReceiptRepository
) : ViewModel() {


    val state: StateFlow<State> = receiptRepository.receiptStream.map { list ->
        val todayReceipts = list.filter { Date().days == it.date.days }
        State(
            receipts = todayReceipts,
            totalAmount = todayReceipts.sumOf { it.amount },
            totalCash = todayReceipts.sumOf { it.cashPayment },
            totalRemaining = todayReceipts.sumOf { it.remainingPayment },
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
        val loading:Boolean = true,
    )
}