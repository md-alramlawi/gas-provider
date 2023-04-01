package com.alramlawi.gasprovider.data.repository.receipt

import com.alramlawi.gasprovider.data.Result
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import kotlinx.coroutines.flow.Flow

interface ReceiptRepository {

    val receiptStream: Flow<List<ReceiptEntity>>

    suspend fun getReceipt(id: String): ReceiptEntity?

    suspend fun addReceipt(receiptEntity: ReceiptEntity): Result<Unit>

    suspend fun updateReceipt(receiptEntity: ReceiptEntity): Result<Unit>

    suspend fun deleteReceipt(id: String): Result<Unit>

    suspend fun refresh()
}