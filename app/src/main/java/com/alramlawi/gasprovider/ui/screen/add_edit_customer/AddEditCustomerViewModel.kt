package com.alramlawi.gasprovider.ui.screen.add_edit_customer

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alramlawi.gasprovider.data.Result
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import com.alramlawi.gasprovider.data.repository.customer.CustomerRepository
import com.alramlawi.gasprovider.ui.screen.IdArgs
import com.alramlawi.gasprovider.utils.Validator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditCustomerViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val savedStateHandle: SavedStateHandle,
    private val validator: Validator
) : ViewModel() {

    private var _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> get() = _state

    init {
        loadCustomer()
    }

    private fun loadCustomer() {
        viewModelScope.launch {
            IdArgs(savedStateHandle).id?.let { id ->
                customerRepository.getCustomer(id)?.let { c ->
                    _state.update { it.copy(customer = c, isNewCustomer = false) }
                }
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

    fun onIdNumberChanged(txt: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                customer = _state.value.customer.copy(idNumber = txt),
                idError = false,
                saveComplete = false
            )
        }
    }

    fun onMobileChanged(txt: String) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                customer = _state.value.customer.copy(mobile = txt),
                mobileError = false,
                saveComplete = false
            )
        }
    }

    fun saveCustomer() {
        viewModelScope.launch {
            _state.value.customer.let { c ->

                if (
                    !validator.isValidName(c.firstName)
                    || !validator.isValidName(c.lastName)
                    || !validator.isValidNumber(c.mobile)
                    || !validator.isValidNumber(c.idNumber)
                ) {
                    _state.update {
                        it.copy(
                            firstNameError = !validator.isValidName(c.firstName),
                            lastNameError = !validator.isValidName(c.lastName),
                            mobileError = !validator.isValidNumber(c.mobile),
                            idError = !validator.isValidNumber(c.idNumber)
                        )
                    }

                    return@launch
                }

                _state.update {it.copy(loading = true)}
                val remoteResult = if (_state.value.isNewCustomer) {
                    customerRepository.addCustomer(c)
                } else {
                    customerRepository.updateCustomer(c)
                }
                _state.update {it.copy(loading = false)}

                if(remoteResult is Result.Success){
                    _state.update {
                        it.copy(
                            customer = CustomerEntity(),
                            saveComplete = true
                        )
                    }
                }
            }
        }
    }

    data class State(
        val loading: Boolean = false,
        val customer: CustomerEntity = CustomerEntity(),
        val idError: Boolean = false,
        val mobileError: Boolean = false,
        val firstNameError: Boolean = false,
        val lastNameError: Boolean = false,
        val isNewCustomer: Boolean = true,
        val saveComplete: Boolean = false
    )
}