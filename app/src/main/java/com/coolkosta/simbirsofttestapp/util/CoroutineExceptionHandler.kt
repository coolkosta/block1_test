package com.coolkosta.simbirsofttestapp.util

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler

object CoroutineExceptionHandler {
    fun create(
        tag: String,
        onError: () -> Unit
    ): CoroutineExceptionHandler {
        return CoroutineExceptionHandler { _, exception ->
            Log.e(tag, "CoroutineExceptionHandler got $exception")
            onError()
        }
    }
}