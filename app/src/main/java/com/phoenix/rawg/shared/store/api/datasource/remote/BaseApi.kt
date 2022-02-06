package com.phoenix.rawg.shared.store.api.datasource.remote

import com.phoenix.rawg.shared.network.ResultWrapper
import com.phoenix.rawg.shared.store.models.response.ResponseWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

abstract class BaseApi {

    suspend fun <T : Any> safeApiCall(
        dispatcher: CoroutineDispatcher,
        apiCall: suspend () -> ResponseWrapper<T>
    ) =
        withContext(dispatcher) {
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ResultWrapper.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        ResultWrapper.GenericError(code, throwable)
                    }
                    else -> {
                        ResultWrapper.GenericError(null, null)
                    }
                }
            }
        }
}