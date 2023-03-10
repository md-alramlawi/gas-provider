package com.alramlawi.gasprovider.data.local.datasource.receipt

import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeReceiptLocalDataSource(private val receiptsData: LinkedHashMap<String, ReceiptEntity>): ReceiptLocalDataSource{

    override val receiptsStream: Flow<List<ReceiptEntity>>
        get() = flow { emit(receiptsData.map { it.value } )}

    override suspend fun getReceipt(id: String): ReceiptEntity? {
        return receiptsData[id]
    }

    override suspend fun saveReceipt(receiptEntity: ReceiptEntity) {
        receiptsData[receiptEntity.id] = receiptEntity
    }

    override suspend fun updateReceipt(receiptEntity: ReceiptEntity) {
        receiptsData[receiptEntity.id] = receiptEntity
    }

    override suspend fun deleteReceipt(id: String) {
        receiptsData.remove(id)
    }

}