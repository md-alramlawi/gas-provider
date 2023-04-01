package com.alramlawi.gasprovider.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import com.alramlawi.gasprovider.data.repository.receipt.ReceiptRepository
import com.alramlawi.gasprovider.utils.days
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val receiptRepository: ReceiptRepository
) : ViewModel() {


    private var _screenState: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState())
    val screenState: StateFlow<ScreenState> get() = _screenState

    val state: StateFlow<State> = receiptRepository.receiptStream.map { list ->
        val todayReceipts = list.filter { Date().days == it.date.days }
        State(
            receipts = todayReceipts,
            totalAmount = todayReceipts.sumOf { it.amount },
            totalCash = todayReceipts.sumOf { it.cashPayment },
            totalRemaining = todayReceipts.sumOf { it.remainingPayment },
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), State())


    init {
        viewModelScope.launch {
            receiptRepository.refresh()
        }
    }

    fun deleteReceipt(receipt: ReceiptEntity) {
        viewModelScope.launch {
            _screenState.update { it.copy(loading = true) }
            receiptRepository.deleteReceipt(receipt.id)
            _screenState.update { it.copy(loading = false) }
        }
    }

    data class State(
        val receipts: List<ReceiptEntity> = emptyList(),
        val totalAmount: Double = 0.0,
        val totalCash: Double = 0.0,
        val totalRemaining: Double = 0.0,
    )

    data class ScreenState(
        val isAdmin: Boolean = true,
        val loading: Boolean = false,
    )
}