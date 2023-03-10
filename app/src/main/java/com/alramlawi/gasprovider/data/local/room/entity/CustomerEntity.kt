package com.alramlawi.gasprovider.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "customer_entity")
data class CustomerEntity @JvmOverloads constructor(
    @ColumnInfo(name = "first_name") var firstName: String,
    @ColumnInfo(name = "last_name") var lastName: String,
    @ColumnInfo(name = "last_update") var lastUpdate: Date = Date(),
    @PrimaryKey @ColumnInfo(name = "id") var id: String = UUID.randomUUID().toString()
){
    val isEmpty: Boolean get() = firstName.isEmpty() || lastName.isEmpty()
    val fullName: String get() = "$firstName $lastName"
}
