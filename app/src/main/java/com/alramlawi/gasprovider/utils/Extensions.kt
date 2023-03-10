package com.alramlawi.gasprovider.utils

import java.text.DateFormat
import java.util.*

val Date.days: Int
    get() = Calendar.getInstance().let {
        it.time = this
        it.get(Calendar.DAY_OF_YEAR) + it.get(Calendar.YEAR) * 365
    }

val Date.localString: String
    get() = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.SHORT).format(this)