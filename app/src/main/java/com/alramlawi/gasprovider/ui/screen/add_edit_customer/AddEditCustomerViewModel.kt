package com.alramlawi.gasprovider.ui.screen.add_edit_customer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import com.alramlawi.gasprovider.data.repository.customer.CustomerRepository
import com.alramlawi.gasprovider.ui.screen.IdArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditCustomerViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val args: IdArgs = IdArgs(savedStateHandle)

    private var _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> get() = _state

    init {
        val id = args.id
        if (!id.isNullOrEmpty()) {
            fetchCustomer(id)
        }
    }

    private fun fetchCustomer(id: String) {
        viewModelScope.launch {
            customerRepository.getCustomer(id)?.let {
                _state.emit(_state.value.copy(customer = it, isNewCustomer = false))
            }
        }
    }

    fun onFirstNameChanged(txt: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                customer = _state.value.customer.copy(firstName = txt),
                firstNameError = false,
                saveComplete = false
            )
        }
    }

    fun onLastNameChanged(txt: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                customer = _state.value.customer.copy(lastName = txt),
                lastNameError = false,
                saveComplete = false
            )
        }
    }


    fun saveCustomer() {
        viewModelScope.launch {
            _state.value.customer.let { customer ->

                if(customer.isEmpty){
                    _state.value = _state.value.copy(
                        firstNameError = customer.firstName.isEmpty(),
                        lastNameError = customer.lastName.isEmpty()
                    )
                    return@launch
                }

                if (_state.value.isNewCustomer) {
                    customerRepository.addCustomer(customer)
                } else {
                    customerRepository.updateCustomer(customer)
                }

                _state.emit(_state.value.copy(customer = CustomerEntity("", ""), saveComplete = true))

            }
        }
    }

    data class State(
        val customer: CustomerEntity = CustomerEntity("", ""),
        val firstNameError: Boolean = false,
        val lastNameError: Boolean = false,
        val isNewCustomer: Boolean = true,
        val saveComplete: Boolean = false
    )
}