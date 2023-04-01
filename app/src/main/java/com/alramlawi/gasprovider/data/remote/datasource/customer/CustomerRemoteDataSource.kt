package com.alramlawi.gasprovider.data.remote.datasource.customer

import com.alramlawi.gasprovider.data.Result
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity

interface CustomerRemoteDataSource {

    suspend fun getCustomers(): Result<List<CustomerEntity>>

    suspend fun saveCustomer(customerEntity: CustomerEntity): Result<Unit>

    suspend fun updateCustomer(customerEntity: CustomerEntity): Result<Unit>

    suspend fun updateActivityLastDate(id: String): Result<Unit>

    suspend fun deleteCustomer(id: String): Result<Unit>
}
