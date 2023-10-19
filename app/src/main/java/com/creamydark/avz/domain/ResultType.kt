package com.creamydark.avz.domain

sealed class ResultType<out T> {
    data class Success<out T>(val data: T) : ResultType<T>()
    data class Error(val message: String) : ResultType<Nothing>()
    object Loading : ResultType<Nothing>()

    companion object {
        fun <T> success(data: T): ResultType<T> = Success(data)
        fun error(message: String): ResultType<Nothing> = Error(message)
        fun loading(): ResultType<Nothing> = Loading
    }
}