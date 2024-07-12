package com.jakub.zajac.station.resource

import retrofit2.Response

sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : ApiResult<T>()
    data class Error(val exception: ApiException) : ApiResult<Nothing>()
}

sealed class ApiException(override val message: String): Exception() {

    sealed class ApiBaseException(override val message: String) :
        ApiException("Ogólny błąd po stronie serwera") {
        data object CustomApiException : ApiBaseException("Serwer chwilowo niedostępny")
    }

    sealed class NetworkBaseException(override val message: String) :
        ApiException("General Network Error") {
        data object NoConnectionException : NetworkBaseException("Brak dostępu do Internetu")
        data object InternalException : NetworkBaseException("Błąd po stronie serwera")
    }
}


suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): ApiResult<T> {
    val response: Response<T>
    try {
        response = call.invoke()
    } catch (t: Throwable) {
        return ApiResult.Error(mapNetworkThrowable(t))
    }

    return if (!response.isSuccessful) {
        ApiResult.Error(ApiException.ApiBaseException.CustomApiException)
    } else {
        response.body()?.let {
            ApiResult.Success(it)
        } ?: run {
            ApiResult.Error(ApiException.ApiBaseException.CustomApiException)
        }
    }
}

private fun mapNetworkThrowable(throwable: Throwable): ApiException {
    return when (throwable) {
        is java.net.UnknownHostException -> ApiException.NetworkBaseException.NoConnectionException
        is java.net.SocketTimeoutException -> ApiException.NetworkBaseException.NoConnectionException
        else -> ApiException.NetworkBaseException.InternalException
    }
}
