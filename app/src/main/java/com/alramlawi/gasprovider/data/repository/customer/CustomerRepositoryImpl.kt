package com.alramlawi.gasprovider.data.repository.customer

import com.alramlawi.gasprovider.data.local.datasource.customer.CustomerLocalDataSource
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CustomerRepositoryImpl(
    private val customerDatasource: CustomerLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CustomerRepository {

    override val customersStream: Flow<List<CustomerEntity>>
        get() = customerDatasource.customersStream

    override suspend fun getCustomer(id: String): CustomerEntity? =
        customerDatasource.getCustomer(id)


    override suspend fun addCustomer(customerEntity: CustomerEntity) {
        withContext(ioDispatcher) {
            customerDatasource.saveCustomer(customerEntity)
        }
    }

    override suspend fun updateCustomer(customerEntity: CustomerEntity) =
        withContext(ioDispatcher) {
            customerDatasource.updateCustomer(customerEntity)
        }

    override suspend fun deleteCustomer(id: String) = withContext(ioDispatcher) {
        customerDatasource.deleteCustomer(id)
    }
}