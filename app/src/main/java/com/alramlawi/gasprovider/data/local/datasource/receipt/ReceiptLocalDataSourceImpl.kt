package com.alramlawi.gasprovider.data.local.datasource.receipt

import com.alramlawi.gasprovider.data.local.room.dao.ReceiptDao
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ReceiptLocalDataSourceImpl(
    private val receiptDao: ReceiptDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ReceiptLocalDataSource {

    override val receiptsStream: Flow<List<ReceiptEntity>>
        get() = receiptDao.flowReceipts().map { list -> list.sortedByDescending { it.date } }

    override suspend fun getReceipt(id: String): ReceiptEntity? =
        receiptDao.getReceiptById(id)

    override suspend fun saveReceipt(receiptEntity: ReceiptEntity) =
        withContext(ioDispatcher) { receiptDao.insertReceipt(receiptEntity) }

    override suspend fun updateReceipt(receiptEntity: ReceiptEntity) =
        withContext(ioDispatcher) { receiptDao.updateReceipt(receiptEntity) }

    override suspend fun deleteReceipt(id: String) =
        withContext(ioDispatcher) { receiptDao.deleteReceiptById(id) }

    override suspend fun deleteAll() {
        withContext(ioDispatcher) { receiptDao.deleteAll() }
    }
}