package com.alramlawi.gasprovider.data.local.datasource.customer

import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.LinkedHashMap

class FakeCustomerLocalDataSource(private val customersData: LinkedHashMap<String, CustomerEntity>): CustomerLocalDataSource{

    override val customersStream: Flow<List<CustomerEntity>>
        get() = flow{ emit(customersData.map { it.value }) }

    override suspend fun getCustomers(): List<CustomerEntity> {
        return customersData.map { it.value }
    }

    override suspend fun getCustomer(id: String): CustomerEntity? {
        return customersData[id]
    }

    override suspend fun saveCustomer(customerEntity: CustomerEntity) {
        customersData[customerEntity.id] = customerEntity
    }

    override suspend fun updateCustomer(customerEntity: CustomerEntity) {
        customersData[customerEntity.id] = customerEntity
    }

    override suspend fun updateActivityLastDate(id: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCustomer(id: String) {
        customersData.remove(id)
    }

    override suspend fun deleteAll() {
        TODO("Not yet implemented")
    }

}