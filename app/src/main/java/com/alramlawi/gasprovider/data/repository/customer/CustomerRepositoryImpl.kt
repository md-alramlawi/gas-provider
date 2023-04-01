package com.alramlawi.gasprovider.data.repository.customer

import com.alramlawi.gasprovider.data.Result
import com.alramlawi.gasprovider.data.local.datasource.customer.CustomerLocalDataSource
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import com.alramlawi.gasprovider.data.remote.datasource.customer.CustomerRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import java.util.*

class CustomerRepositoryImpl(
    private val customerLocalDatasource: CustomerLocalDataSource,
    private val customerRemoteDatasource: CustomerRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CustomerRepository {

    override val customersStream: Flow<List<CustomerEntity>>
        get() = customerLocalDatasource.customersStream

    override suspend fun getCustomer(id: String): CustomerEntity? =
        customerLocalDatasource.getCustomer(id)


    override suspend fun addCustomer(customerEntity: CustomerEntity): Result<Unit> =
        withContext(ioDispatcher) {
            customerRemoteDatasource.saveCustomer(customerEntity).apply {
                if (this is Result.Success) {
                    refresh()
                }
            }
        }

    override suspend fun updateCustomer(customerEntity: CustomerEntity): Result<Unit> =
        withContext(ioDispatcher) {
            customerRemoteDatasource.updateCustomer(customerEntity).apply {
                if (this is Result.Success) {
                    refresh()
                }
            }
        }

    override suspend fun updateActivityLastDate(id: String): Result<Unit> = withContext(ioDispatcher){
        customerLocalDatasource.getCustomer(id)?.let {
            updateCustomer(it.copy(lastUpdate = Date()))
        } ?: Result.Error(Exception("Failed to update activity last date"))
    }

    override suspend fun deleteCustomer(id: String): Result<Unit> = withContext(ioDispatcher) {
        customerRemoteDatasource.deleteCustomer(id).apply {
            if (this is Result.Success) {
                refresh()
            }
        }
    }

    private suspend fun refresh() = withContext(ioDispatcher) {
        customerRemoteDatasource.getCustomers().apply {
            if (this is Result.Success) {
                customerLocalDatasource.deleteAll()
                this.data.orEmpty().forEach {
                    customerLocalDatasource.saveCustomer(it)
                }
            }
        }
    }
}