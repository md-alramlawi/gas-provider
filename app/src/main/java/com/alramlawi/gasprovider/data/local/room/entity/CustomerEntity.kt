package com.alramlawi.gasprovider.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "customer_entity")
data class CustomerEntity @JvmOverloads constructor(
    @ColumnInfo(name = "id_number") var idNumber: String = "",
    @ColumnInfo(name = "first_name") var firstName: String = "",
    @ColumnInfo(name = "last_name") var lastName: String = "",
    @ColumnInfo(name = "mobile_number") var mobile: String = "",
    @PrimaryKey @ColumnInfo(name = "id") var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "last_update") var lastUpdate: Date = Date()
) {
    val isEmpty: Boolean get() = firstName.isEmpty() || lastName.isEmpty() || mobile.isEmpty() || id.isEmpty()
    val fullName: String get() = "$firstName $lastName"
}
