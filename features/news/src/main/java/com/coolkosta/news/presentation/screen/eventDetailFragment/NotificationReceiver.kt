package com.coolkosta.news.presentation.screen.eventDetailFragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent


class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_REMINDER_LATER) {
            intent.action = ACTION_SHOW
            val pendingIntent =
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            val triggerTime = System.currentTimeMillis() + REMINDER_DELAY_MS
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            NotificationUtils.cancelCurrent(context)
        } else if (intent.action == ACTION_SHOW) {
            NotificationUtils.showRemindedNotification(intent, context)
        }
    }

    companion object {
        const val REMINDER_DELAY_MS = 30 * 60 * 1000
        const val ACTION_REMINDER_LATER = "remind_later"
        const val ACTION_SHOW = "action_show"
    }
}