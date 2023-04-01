package com.alramlawi.gasprovider.data.repository.customer

import com.alramlawi.gasprovider.data.Result
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

    override suspend fun addCustomer(customerEntity: CustomerEntity): Result<Unit> {
        customersData[customerEntity.id] = customerEntity
        return Result.Success(Unit)
    }

    override suspend fun updateCustomer(customerEntity: CustomerEntity): Result<Unit> {
        customersData[customerEntity.id] = customerEntity
        return Result.Success(Unit)
    }

    override suspend fun updateActivityLastDate(id: String): Result<Unit> {
        return Result.Success(Unit)
    }

    override suspend fun deleteCustomer(id: String): Result<Unit> {
        customersData.remove(id)
        return Result.Success(Unit)
    }
}