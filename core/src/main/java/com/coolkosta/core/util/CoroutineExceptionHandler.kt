package com.coolkosta.core.util
import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

object CoroutineExceptionHandler {
    fun create(
        tag: String,
        onError: (message: String) -> Unit
    ): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            Log.e(tag, "com.coolkosta.core.util.CoroutineExceptionHandler got $exception")
            onError(exception.message?: "Unknown error")
        }
    }
}