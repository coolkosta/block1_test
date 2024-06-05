package com.coolkosta.simbirsofttestapp.util

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

object CoroutineExceptionHandler {
    fun createCoroutineExceptionHandler(
        tag: String,
        onError: (Throwable) -> Unit
    ): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            Log.e(tag, "CoroutineExceptionHandler got $exception")
            onError(exception)
        }
    }
}