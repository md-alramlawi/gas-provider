package com.alramlawi.gasprovider.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "receipt_entity")
data class ReceiptEntity @JvmOverloads constructor(
    @ColumnInfo(name = "customer_id") var customerId: String = "",
    @ColumnInfo(name = "customer_name") var customerName: String = "",
    @ColumnInfo(name = "amount") var amount: Double = 0.0,
    @ColumnInfo(name = "cash_payment") var cashPayment: Double = 0.0,
    @ColumnInfo(name = "remaining_payment") var remainingPayment: Double = 0.0,
    @ColumnInfo(name = "date") var date: Date = Date(),
    @PrimaryKey @ColumnInfo(name = "id") var id: String = UUID.randomUUID().toString()
)
