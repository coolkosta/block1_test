package com.coolkosta.news.util

import android.content.Intent
import javax.inject.Inject

class IntentActivity @Inject constructor(private val intent: Intent) {
    fun getActivityIntent(): Intent{
        return intent
    }
}