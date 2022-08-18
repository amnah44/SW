package com.amnah.sw.data

sealed class Status<out T> {
    object OnLoading : Status<Nothing>()
    data class OnSuccess<T>(val data: T?) : Status<T>()
    data class OnError(val message: String?) : Status<Nothing>()
}
