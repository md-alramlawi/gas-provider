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


    private var _screenState: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState())
    val screenState: StateFlow<ScreenState> get() = _screenState


    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    val listState: StateFlow<List<CustomerEntity>> = _searchQuery.combine(customerRepository.customersStream){ query, customers ->
        customers.filter { it.fullName.lowercase().contains(query.lowercase()) }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    fun deleteCustomer(customer: CustomerEntity){
        viewModelScope.launch {
            _screenState.update { it.copy(loading = true) }
            customerRepository.deleteCustomer(customer.id)
            _screenState.update { it.copy(loading = false) }

        }
    }

    fun filterCustomers(query: String){
        viewModelScope.launch {
            _searchQuery.value = query
        }
    }


    data class ScreenState(
        val isAdmin: Boolean = true,
        val loading: Boolean = false
    )
}