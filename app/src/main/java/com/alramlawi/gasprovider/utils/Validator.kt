package com.alramlawi.gasprovider.utils

import android.util.Patterns

class Validator {
    fun isValidEmail(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun isValidName(name: String): Boolean = name.isNotBlank()

    fun isValidNumber(number: String): Boolean = number.all { char -> char.isDigit() } && number.isNotBlank()

}