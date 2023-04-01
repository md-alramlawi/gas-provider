package com.alramlawi.gasprovider.data.remote.datasource.receipts

import com.alramlawi.gasprovider.data.Result
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import com.alramlawi.gasprovider.data.mapper.toHashMap
import com.alramlawi.gasprovider.data.mapper.toReceipt
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ReceiptFirebaseDataSourceImpl(
    private val firebaseStore: FirebaseFirestore
) : ReceiptRemoteDataSource {

    companion object {
        private const val RECEIPT_TABLE = "receipts"
    }

    override suspend fun loadAllReceipts(): Result<List<ReceiptEntity>> {
        return try {
            firebaseStore
                .collection(RECEIPT_TABLE)
                .get()
                .await()
                .documents.map { it.toReceipt }
                .run { Result.Success(this) }

        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun saveReceipt(receiptEntity: ReceiptEntity): Result<Unit> {
        return try {
            firebaseStore
                .collection(RECEIPT_TABLE)
                .document(receiptEntity.id)
                .set(receiptEntity.toHashMap)
                .await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun updateReceipt(receiptEntity: ReceiptEntity): Result<Unit> {
        return try {
            firebaseStore
                .collection(RECEIPT_TABLE)
                .document(receiptEntity.id)
                .update(receiptEntity.toHashMap)
                .await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun deleteReceipt(id: String): Result<Unit> {
        return try {
            firebaseStore
                .collection(RECEIPT_TABLE)
                .document(id)
                .delete()
                .await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

}