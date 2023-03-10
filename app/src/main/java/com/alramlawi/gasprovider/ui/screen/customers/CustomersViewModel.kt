package com.alramlawi.gasprovider.ui.screen.customers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import com.alramlawi.gasprovider.data.repository.customer.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomersViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {


    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    val state: StateFlow<State> = _searchQuery.combine(customerRepository.customersStream){query, customers ->
        State(
            customers = customers.filter { it.fullName.lowercase().contains(query.lowercase()) },
            loading = false
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), State())


    fun deleteCustomer(customer: CustomerEntity){
        viewModelScope.launch {
            customerRepository.deleteCustomer(customer.id)
        }
    }

    fun filterCustomers(query: String){
        viewModelScope.launch {
            _searchQuery.value = query
        }
    }


    data class State(
        val customers: List<CustomerEntity> = emptyList(),
        val isAdmin: Boolean = true,
        val loading: Boolean = true
    )
}