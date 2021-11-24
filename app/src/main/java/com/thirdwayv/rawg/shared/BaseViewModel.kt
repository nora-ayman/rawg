package com.thirdwayv.rawg.shared

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thirdwayv.rawg.shared.store.models.enums.Exceptions
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.net.ssl.HttpsURLConnection

abstract class BaseViewModel : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val exception = MutableLiveData<Exceptions?>(null)

    fun handleError(throwable: Throwable, retry: (Boolean) -> Unit) {
        val mappedException = when (throwable) {
                is HttpException -> {
                    when (throwable.code()) {
                        HttpsURLConnection.HTTP_INTERNAL_ERROR -> {
                            retry.invoke(false)
                            Exceptions.ServerError
                        }
                        HttpsURLConnection.HTTP_CLIENT_TIMEOUT, HttpsURLConnection.HTTP_GATEWAY_TIMEOUT -> {
                            retry.invoke(true)
                            Exceptions.TimeOutError
                        }
                        HttpsURLConnection.HTTP_BAD_REQUEST -> {
                            retry.invoke(false)
                            Exceptions.BadRequestError
                        }
                        HttpsURLConnection.HTTP_FORBIDDEN -> {
                            retry.invoke(true)
                            Exceptions.ForbiddenError
                        }
                        else -> {
                            retry.invoke(false)
                            Exceptions.UnknownError
                        }
                    }
                }
                is UnknownHostException -> {
                    retry.invoke(false)
                    Exceptions.NetworkError
                }
                else -> null
            }

        exception.postValue(mappedException)
    }
}