package com.carbonanik.bongotalkies.repositories.components

import io.ktor.client.features.*
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.nio.charset.Charset

fun <T> tryFetchRequest(
    fetch: suspend () -> T,
): Flow<NetworkState<T>> = flow {
    emit(NetworkState.Loading())

    try {
        emit(
            NetworkState.Success(fetch())
        )
    } catch (e: ResponseException) {
        println("ResponseException")
        println(e.localizedMessage)
        emit(
            NetworkState.Error(e.response.readText(Charset.defaultCharset()))
        )
    } catch (e: Exception) {
        println("Exception")
        println(e.javaClass.name)
        println(e.localizedMessage)
        println(e.printStackTrace())
    }
}