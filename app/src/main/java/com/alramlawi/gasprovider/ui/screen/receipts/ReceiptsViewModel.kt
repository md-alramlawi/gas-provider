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

    private var _screenState: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState())
    val screenState: StateFlow<ScreenState> get() = _screenState


    val listState: StateFlow<List<ReceiptEntity>> = receiptRepository.receiptStream.map {
        it
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun deleteReceipt(receipt: ReceiptEntity){
        viewModelScope.launch {
            _screenState.update { it.copy(loading = true) }
            receiptRepository.deleteReceipt(receipt.id)
            _screenState.update { it.copy(loading = false) }
        }
    }


    data class ScreenState(
        val isAdmin: Boolean = true,
        val loading: Boolean = false
    )
}