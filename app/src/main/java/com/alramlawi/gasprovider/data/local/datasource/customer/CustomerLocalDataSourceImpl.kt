package com.alramlawi.gasprovider.data.local.datasource.customer

import com.alramlawi.gasprovider.data.local.room.dao.CustomerDao
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import java.util.*

class CustomerLocalDataSourceImpl(
    private val dao: CustomerDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CustomerLocalDataSource {

    override val customersStream: Flow<List<CustomerEntity>>
        get() = dao.flowCustomers().map { list -> list.sortedByDescending { it.lastUpdate } }

    override suspend fun getCustomers(): List<CustomerEntity> =
        dao.getCustomers()

    override suspend fun getCustomer(id: String): CustomerEntity? =
        dao.getCustomerById(id)

    override suspend fun saveCustomer(customerEntity: CustomerEntity) =
        withContext(ioDispatcher) { dao.insertCustomer(customerEntity) }

    override suspend fun updateCustomer(customerEntity: CustomerEntity) =
        withContext(ioDispatcher) { dao.updateCustomer(customerEntity) }

    override suspend fun updateActivityLastDate(id: String) {
        withContext(ioDispatcher) { dao.updateCustomer(dao.getCustomerById(id)?.copy(lastUpdate = Date()) ?: return@withContext) }
    }

    override suspend fun deleteCustomer(id: String) =
        withContext(ioDispatcher) { dao.deleteCustomerById(id) }
}