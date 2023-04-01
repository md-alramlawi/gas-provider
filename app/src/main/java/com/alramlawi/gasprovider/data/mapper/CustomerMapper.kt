package com.alramlawi.gasprovider.data.mapper

import com.alramlawi.gasprovider.data.local.room.entity.CustomerEntity
import com.google.firebase.firestore.DocumentSnapshot
import java.util.*
import kotlin.collections.HashMap

val CustomerEntity.toHashMap: HashMap<String, Any>
    get() = hashMapOf(
        "id_number" to idNumber,
        "first_name" to firstName,
        "last_name" to lastName,
        "mobile_number" to mobile,
        "last_update" to lastUpdate.time
    )


val DocumentSnapshot.toCustomer: CustomerEntity
get() = CustomerEntity(
    id = this.id,
    idNumber = data?.get("id_number") as String,
    firstName = data?.get("first_name") as String,
    lastName = data?.get("last_name") as String,
    mobile = data?.get("mobile_number") as String,
    lastUpdate = (data?.get("last_update") as Long).run { Date(this) }
)