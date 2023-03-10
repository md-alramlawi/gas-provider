package com.alramlawi.gasprovider.data.local.datasource.receipt

import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import kotlinx.coroutines.flow.Flow

interface ReceiptLocalDataSource {

    val receiptsStream: Flow<List<ReceiptEntity>>

    suspend fun getReceipt(id: String): ReceiptEntity?

    suspend fun saveReceipt(receiptEntity: ReceiptEntity)

    suspend fun updateReceipt(receiptEntity: ReceiptEntity)

    suspend fun deleteReceipt(id: String)
}
