package com.alramlawi.gasprovider.data.repository.receipt

import com.alramlawi.gasprovider.data.local.datasource.customer.CustomerLocalDataSource
import com.alramlawi.gasprovider.data.local.datasource.receipt.ReceiptLocalDataSource
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext

class ReceiptRepositoryImpl(
    private val receiptLocalDataSource: ReceiptLocalDataSource,
    private val customerLocalDataSource: CustomerLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ReceiptRepository {

    override val receiptStream: Flow<List<ReceiptEntity>>
        get() = receiptLocalDataSource.receiptsStream
            .combine(
                customerLocalDataSource.customersStream
            ) { receipts, customers ->
                receipts.map { receipt ->
                    receipt.copy(
                        customerName = customers.find { receipt.customerId == it.id }?.fullName
                            ?: ""
                    )
                }
            }

    override suspend fun getReceipt(id: String): ReceiptEntity? =
        receiptLocalDataSource.getReceipt(id)

    override suspend fun addReceipt(receiptEntity: ReceiptEntity) = withContext(ioDispatcher) {
        receiptLocalDataSource.saveReceipt(receiptEntity)
        customerLocalDataSource.updateActivityLastDate(receiptEntity.customerId)
    }

    override suspend fun updateReceipt(receiptEntity: ReceiptEntity) = withContext(ioDispatcher) {
        receiptLocalDataSource.updateReceipt(receiptEntity)
        customerLocalDataSource.updateActivityLastDate(receiptEntity.customerId)
    }

    override suspend fun deleteReceipt(id: String) = withContext(ioDispatcher) {
        receiptLocalDataSource.deleteReceipt(id)
    }
}