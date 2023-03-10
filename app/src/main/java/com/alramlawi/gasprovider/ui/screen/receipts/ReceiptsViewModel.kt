package com.alramlawi.gasprovider.ui.screen.receipts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import com.alramlawi.gasprovider.data.repository.receipt.ReceiptRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptsViewModel @Inject constructor(
    private val receiptRepository: ReceiptRepository
) : ViewModel() {


    val state: StateFlow<State> = receiptRepository.receiptStream.map {
        State(receipts = it, loading = false)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), State())

    fun deleteReceipt(receipt: ReceiptEntity){
        viewModelScope.launch {
            receiptRepository.deleteReceipt(receipt.id)
        }
    }

    data class State(
        val receipts: List<ReceiptEntity> = emptyList(),
        val isAdmin: Boolean = true,
        val loading :Boolean = true,
    )
}