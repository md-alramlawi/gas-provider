package com.alramlawi.gasprovider.data.repository.customer

import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import kotlinx.coroutines.flow.Flow
import com
.alramlawi.gasprovider.data.Result

interface CustomerRepository {

    val customersStream: Flow<List<CustomerEntity>>

    suspend fun getCustomer(id: String): CustomerEntity?

    suspend fun addCustomer(customerEntity: CustomerEntity): Result<Unit>

    suspend fun updateCustomer(customerEntity: CustomerEntity): Result<Unit>

    suspend fun updateActivityLastDate(id: String): Result<Unit>

    suspend fun deleteCustomer(id: String): Result<Unit>
}