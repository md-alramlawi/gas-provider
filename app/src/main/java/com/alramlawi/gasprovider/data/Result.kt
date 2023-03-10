package com.alramlawi.gasprovider.data

sealed class Result<T>(val data: T? = null, val ex: Exception? = null) {
    class Success<T>(data: T) : Result<T>(data)
    class Error<T>(ex: Exception, data: T? = null) : Result<T>(data, ex)
}