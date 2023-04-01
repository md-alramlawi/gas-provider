package com.alramlawi.gasprovider.data.repository.receipt

import com.alramlawi.gasprovider.data.Result
import com.alramlawi.gasprovider.data.local.datasource.receipt.ReceiptLocalDataSource
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import com.alramlawi.gasprovider.data.remote.datasource.receipts.ReceiptRemoteDataSource
import com.alramlawi.gasprovider.data.repository.customer.CustomerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext

class ReceiptRepositoryImpl(
    private val receiptLocalDataSource: ReceiptLocalDataSource,
    private val receiptRemoteDataSource: ReceiptRemoteDataSource,
    private val customerRepository: CustomerRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ReceiptRepository {

    override val receiptStream: Flow<List<ReceiptEntity>>
        get() = receiptLocalDataSource.receiptsStream
            .combine(
                customerRepository.customersStream
            ) { receipts, customers ->
                receipts.map { receipt ->
                    receipt.copy(
                        customerName = customers.find { receipt.customerId == it.id }?.fullName
                            ?: ""
                    )
                }.filter { r -> customers.map { it.id }.contains(r.customerId) }
            }

    override suspend fun getReceipt(id: String): ReceiptEntity? =
        receiptLocalDataSource.getReceipt(id)

    override suspend fun addReceipt(receiptEntity: ReceiptEntity) = withContext(ioDispatcher) {
        receiptRemoteDataSource.saveReceipt(receiptEntity).run {
            if (this is Result.Success) {
                customerRepository.updateActivityLastDate(receiptEntity.customerId).let { c ->
                    if (c is Result.Success) {
                        refresh()
                    }
                    c
                }
            } else
                this
        }
    }

    override suspend fun updateReceipt(receiptEntity: ReceiptEntity): Result<Unit> =
        withContext(ioDispatcher) {
            receiptRemoteDataSource.updateReceipt(receiptEntity).run {
                if (this is Result.Success) {
                    customerRepository.updateActivityLastDate(receiptEntity.customerId).let { c ->
                        if (c is Result.Success) {
                            refresh()
                        }
                        c
                    }
                } else
                    this
            }
        }

    override suspend fun deleteReceipt(id: String): Result<Unit> = withContext(ioDispatcher) {
        receiptRemoteDataSource.deleteReceipt(id).run {
            if (this is Result.Success) {
                refresh()
            }
            this
        }
    }

    override suspend fun refresh() = withContext(ioDispatcher) {
        receiptRemoteDataSource.loadAllReceipts().run {
            if (this is Result.Success) {
                receiptLocalDataSource.deleteAll()
                this.data.orEmpty().forEach {
                    receiptLocalDataSource.saveReceipt(it)
                }
            }
        }
    }
}