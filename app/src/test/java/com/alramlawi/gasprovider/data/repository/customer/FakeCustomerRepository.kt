package com.alramlawi.gasprovider.data.repository.customer

import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCustomerRepository : CustomerRepository {

    private val customersData: LinkedHashMap<String, CustomerEntity> = LinkedHashMap()
    override val customersStream: Flow<List<CustomerEntity>>
        get() = flow { emit(customersData.map { it.value }) }

    override suspend fun getCustomer(id: String): CustomerEntity? {
        return customersData[id]
    }

    override suspend fun addCustomer(customerEntity: CustomerEntity) {
        customersData[customerEntity.id] = customerEntity
    }

    override suspend fun updateCustomer(customerEntity: CustomerEntity) {
        customersData[customerEntity.id] = customerEntity
    }

    override suspend fun deleteCustomer(id: String) {
        customersData.remove(id)
    }

    override suspend fun searchQuery(query: String) {
        TODO("Not yet implemented")
    }
}