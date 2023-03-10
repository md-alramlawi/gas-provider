package com.alramlawi.gasprovider.data.repository.customer

import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {

    val customersStream: Flow<List<CustomerEntity>>

    suspend fun getCustomer(id: String): CustomerEntity?

    suspend fun addCustomer(customerEntity: CustomerEntity)

    suspend fun updateCustomer(customerEntity: CustomerEntity)

    suspend fun deleteCustomer(id: String)
}