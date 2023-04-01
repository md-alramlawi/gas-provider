package com.alramlawi.gasprovider.data.local.datasource.customer

import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import kotlinx.coroutines.flow.Flow

interface CustomerLocalDataSource {

    val customersStream: Flow<List<CustomerEntity>>

    suspend fun getCustomers(): List<CustomerEntity>

    suspend fun getCustomer(id: String): CustomerEntity?

    suspend fun saveCustomer(customerEntity: CustomerEntity)

    suspend fun updateCustomer(customerEntity: CustomerEntity)

    suspend fun updateActivityLastDate(id: String)

    suspend fun deleteCustomer(id: String)

    suspend fun deleteAll()
}
