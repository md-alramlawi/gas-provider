package com.alramlawi.gasprovider.data.local.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alramlawi.gasprovider.data.local.room.dao.CustomerDao
import com.alramlawi.gasprovider.data.local.room.dao.ReceiptDao
import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import com.alramlawi.gasprovider.data.local.room.entity.ReceiptEntity

@Database(entities = [CustomerEntity::class, ReceiptEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class GasDatabase : RoomDatabase() {
    abstract val customerDao: CustomerDao
    abstract val receiptDao: ReceiptDao
}
