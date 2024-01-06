package com.creamydark.avz.domain.model

sealed class ResultType<out T> {
    data class Success<out T>(val data: T) : ResultType<T>()
    data class Error(val exception: Throwable) : ResultType<Nothing>()
    object Loading : ResultType<Nothing>()

    companion object {
        fun <T> success(data: T): ResultType<T> = Success(data)
        fun error(exception: Throwable): ResultType<Nothing> = Error(exception)
        fun loading(): ResultType<Nothing> = Loading
    }
}