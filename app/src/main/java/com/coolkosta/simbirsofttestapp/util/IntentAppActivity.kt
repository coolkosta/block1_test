package com.coolkosta.simbirsofttestapp.util

import android.content.Context
import android.content.Intent
import com.coolkosta.simbirsofttestapp.presentation.screen.activity.MainActivity
import javax.inject.Inject

class IntentAppActivity @Inject constructor(
    private val context: Context,
) {
    fun getIntentActivity(): Intent {
        return Intent(context, MainActivity::class.java)
    }
}