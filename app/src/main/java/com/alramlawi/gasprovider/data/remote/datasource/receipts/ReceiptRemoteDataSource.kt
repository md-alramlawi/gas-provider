package com.alramlawi.gasprovider.data.remote.datasource.receipts

import com.alramlawi.gasprovider.data.Result
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity

interface ReceiptRemoteDataSource {

    suspend fun loadAllReceipts(): Result<List<ReceiptEntity>>

    suspend fun saveReceipt(receiptEntity: ReceiptEntity): Result<Unit>

    suspend fun updateReceipt(receiptEntity: ReceiptEntity): Result<Unit>

    suspend fun deleteReceipt(id: String): Result<Unit>
}