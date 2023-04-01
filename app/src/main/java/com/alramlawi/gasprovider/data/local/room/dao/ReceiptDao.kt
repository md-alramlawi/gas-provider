package com.alramlawi.gasprovider.data.local.room.dao

import androidx.room.*
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ReceiptDao {

    @Query("SELECT * FROM receipt_entity")
    fun flowReceipts(): Flow<List<ReceiptEntity>>

    @Query("SELECT * FROM receipt_entity WHERE id = :id")
    suspend fun getReceiptById(id: String): ReceiptEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReceipt(receiptEntity: ReceiptEntity)

    @Update
    suspend fun updateReceipt(receiptEntity: ReceiptEntity)

    @Query("DELETE FROM receipt_entity WHERE id = :id")
    suspend fun deleteReceiptById(id: String)

    @Query("DELETE FROM receipt_entity")
    suspend fun deleteAll()
}
