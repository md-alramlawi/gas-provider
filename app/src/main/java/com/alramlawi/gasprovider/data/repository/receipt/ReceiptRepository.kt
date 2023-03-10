package com.alramlawi.gasprovider.data.repository.receipt

import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import kotlinx.coroutines.flow.Flow

interface ReceiptRepository {

    val receiptStream: Flow<List<ReceiptEntity>>

    suspend fun getReceipt(id: String): ReceiptEntity?

    suspend fun addReceipt(receiptEntity: ReceiptEntity)

    suspend fun updateReceipt(receiptEntity: ReceiptEntity)


    suspend fun deleteReceipt(id: String)
}