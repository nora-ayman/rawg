package com.phoenix.rawg.shared.store.api.datasource.remote

abstract class BaseApi {

    suspend fun <T : Any> safeApiCall(call: suspend () -> com.phoenix.rawg.shared.network.Result<T>, exception: Exception): com.phoenix.rawg.shared.network.Result<T> = try {
        call.invoke()
    } catch (e: Exception) {
        com.phoenix.rawg.shared.network.Result.Error(exception)
    }
}