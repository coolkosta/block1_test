package com.coolkosta.simbirsofttestapp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.coolkosta.simbirsofttestapp.presentation.screen.help_fragment.HelpFragment.Companion.ACTION_SERVICE_FILTER_KEY
import com.coolkosta.simbirsofttestapp.presentation.screen.help_fragment.HelpFragment.Companion.SERVICE_DATA_KEY
import com.coolkosta.simbirsofttestapp.util.Generator

class HelpCategoryService : Service() {

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val thread = Thread {
            Thread.sleep(5000)
            val result = Generator().generateHelpList()
            val broadCastIntent = Intent(ACTION_SERVICE_FILTER_KEY)
            broadCastIntent.setPackage(applicationContext.packageName)
            broadCastIntent.putParcelableArrayListExtra(SERVICE_DATA_KEY, ArrayList(result))
            sendBroadcast(broadCastIntent)
            stopSelf()
        }
        thread.start()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        // We don't provide binding, so return null
        return null
    }
}