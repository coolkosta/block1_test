package com.coolkosta.news.presentation.screen.eventDetailFragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import com.coolkosta.news.domain.model.EventEntity


class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        if (intent != null) {
            val event = IntentCompat.getParcelableExtra(intent, "event", EventEntity::class.java)
            event?.let {
                val fragment = EventDetailFragment.newInstance(event)
                val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
                    .replace(com.coolkosta.core.R.id.fragment_container, fragment)
                    .addToBackStack(null)
                transaction.commit()
            }
        }
    }
}