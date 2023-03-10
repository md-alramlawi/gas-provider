package com.alramlawi.gasprovider.ui.screen.add_edit_receipt

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import com.alramlawi.gasprovider.data.repository.customer.CustomerRepository
import com.alramlawi.gasprovider.data.repository.receipt.ReceiptRepository
import com.alramlawi.gasprovider.ui.screen.IdArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditReceiptViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val receiptRepository: ReceiptRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val args: IdArgs = IdArgs(savedStateHandle)

    private var _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> get() = _state

    private var _customers: List<CustomerEntity> = emptyList()

    init {
        viewModelScope.launch {
            _customers = customerRepository.customersStream.first()
            if (!args.id.isNullOrEmpty()) {
                fetchReceipt(args.id)
            }
        }
    }

    private fun fetchReceipt(id: String) {
        viewModelScope.launch {
            receiptRepository.getReceipt(id)?.let {
                _state.emit(
                    _state.value.copy(
                        receipt = it,
                        selectedCustomer = customerRepository.getCustomer(it.customerId),
                        fullName = customerRepository.getCustomer(it.customerId)?.fullName ?: "",
                        amount = it.amount.toString(),
                        cashPayment = it.cashPayment.toString(),
                        remainingPayment = it.remainingPayment.toString(),
                    )
                )
            }
        }
    }

    fun selectCustomer(customer: CustomerEntity?) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                selectedCustomer = customer,
                customers = emptyList(),
                fullName = customer?.fullName ?: "",
                customerError = false,
                saveComplete = false
            )
        }
    }

    fun onNameChanged(strName: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                fullName = strName,
                customers = _customers.filter {
                    strName.isNotEmpty() && it.fullName.lowercase().contains(strName.lowercase())
                },
                selectedCustomer = _customers.find { it.fullName == strName },
                customerError = false,
                saveComplete = false
            )
        }
    }

    fun onAmountChanged(strAmount: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                amount = strAmount,
                amountError = false,
                saveComplete = false
            )
        }
    }


    fun onCashPaymentChanged(strCash: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                cashPayment = strCash,
                cashError = false,
                saveComplete = false
            )
        }
    }

    fun onRemindingPaymentChanged(strRemind: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                remainingPayment = strRemind,
                remindingError = false,
                saveComplete = false
            )
        }
    }

    fun saveReceipt() {
        viewModelScope.launch {

            _state.value = _state.value.copy(
                customerError = _state.value.selectedCustomer == null,
                amountError = _state.value.amount.toDoubleOrNull() == null,
                cashError = _state.value.cashPayment.toDoubleOrNull() == null,
                remindingError = _state.value.remainingPayment.toDoubleOrNull() == null,
            )

            _state.value.apply {
                if (customerError || amountError || cashError || remindingError) {
                    return@launch
                }

                receipt?.let {
                    receiptRepository.updateReceipt(
                        it.copy(
                            customerId = selectedCustomer!!.id,
                            customerName = selectedCustomer.fullName,
                            amount = amount.toDoubleOrNull() ?: 0.0,
                            cashPayment = cashPayment.toDoubleOrNull() ?: 0.0,
                            remainingPayment = remainingPayment.toDoubleOrNull() ?: 0.0,
                        )
                    )
                } ?: receiptRepository.addReceipt(
                    ReceiptEntity(
                        customerId = selectedCustomer!!.id,
                        customerName = selectedCustomer.fullName,
                        amount = amount.toDoubleOrNull() ?: 0.0,
                        cashPayment = cashPayment.toDoubleOrNull() ?: 0.0,
                        remainingPayment = remainingPayment.toDoubleOrNull() ?: 0.0,
                    )
                )

            }

            _state.emit(
                _state.value.copy(
                    fullName = "",
                    amount = "",
                    cashPayment = "",
                    remainingPayment = "",
                    saveComplete = true
                )
            )

        }
    }

    data class State(
        val customers: List<CustomerEntity> = emptyList(),
        val receipt: ReceiptEntity? = null,
        val selectedCustomer: CustomerEntity? = null,
        val fullName: String = "",
        val amount: String = "",
        val cashPayment: String = "",
        val remainingPayment: String = "",
        val customerError: Boolean = false,
        val amountError: Boolean = false,
        val cashError: Boolean = false,
        val remindingError: Boolean = false,
        val saveComplete: Boolean = false
    )

}