package com.phoenix.rawg.shared.network

sealed class ResultWrapper <out T> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: Exception? = null) : ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()

}
