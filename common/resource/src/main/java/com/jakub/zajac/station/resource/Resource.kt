package com.jakub.zajac.station.resource

sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val exception: Throwable, val message: String? = null) : Resource<Nothing>()
    data class Loading(val isLoading: Boolean = true): Resource<Nothing>()
}
