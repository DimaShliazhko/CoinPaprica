package com.example.coinpaprica.common

sealed class Results<T>(val data: T? = null, val messages: String? = null) {
    class Success<T>(data: T) : Results<T>(data)
    class Error<T>(messages: String, data: T? = null) : Results<T>(data, messages)
    class Loading<T>(data: T? = null) : Results<T>(data)
}