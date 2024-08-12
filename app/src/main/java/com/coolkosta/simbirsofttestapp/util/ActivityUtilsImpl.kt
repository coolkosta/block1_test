package com.coolkosta.simbirsofttestapp.util

import android.content.Context
import android.content.Intent
import com.coolkosta.core.util.ActivityUtils
import com.coolkosta.simbirsofttestapp.presentation.screen.activity.MainActivity
import javax.inject.Inject

class ActivityUtilsImpl @Inject constructor(
    private val context: Context,
) : ActivityUtils {
    override fun getActivityIntent(): Intent {
        return Intent(context, MainActivity::class.java)
    }
}