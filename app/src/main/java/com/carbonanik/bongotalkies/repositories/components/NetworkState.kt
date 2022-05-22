package com.carbonanik.bongotalkies.repositories.components

sealed class NetworkState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val errorMessage: String? = null
) {
    class Loading<T>() : NetworkState<T>(isLoading = true)
    class Success<T>(data: T) : NetworkState<T>(data = data)
    class Error<T>(message: String) : NetworkState<T>(errorMessage = message)
}
