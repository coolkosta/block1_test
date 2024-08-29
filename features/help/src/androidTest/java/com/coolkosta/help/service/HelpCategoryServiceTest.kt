package com.coolkosta.help.service

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HelpCategoryServiceTest {

    @Test
    fun testServiceRunning() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val serviceIntent = Intent(context, HelpCategoryService::class.java)
        context.startService(serviceIntent)
        val isServiceRunning = isServiceRunning(context, HelpCategoryService::class.java)
        assertTrue(isServiceRunning)
        context.stopService(serviceIntent)
        assertTrue(isServiceRunning)
    }

    private fun isServiceRunning(context: Context, serviceClass: Class<out Service>): Boolean {
        val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val services = manager.getRunningServices(Int.MAX_VALUE)
        return services.any { it.service.className == serviceClass.name }
    }
}