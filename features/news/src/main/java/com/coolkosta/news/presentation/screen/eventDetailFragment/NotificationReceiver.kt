package com.coolkosta.news.presentation.screen.eventDetailFragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent

// класс обрабатывает логику нажатия кнопки "напомнить позже", также обрабатывает отложенный PendingIntent на показ уведомления через задержку
// логика такая - при нажатии кнопки отложить срабатывает BroadcastReceiver, приходит интент с action = ACTION_REMINDER_LATER
// мы через alarm manager планируем задачу, но результат этой задачи - тоже PendingIntent, мы не можем из alarm manager через задержку напрямую отобразить уведомление
// для удобства используем этот же BroadcastReceiver. в PendingIntent ложим тот же интенет (который мы сформировали в NotificationUtils, и который хранит инфу о событи),
// но меняем у него action. Когда через задержку приходит intent.action == ACTION_SHOW - вызываем отображение уведомления, но в измененном виде (без кнопки напомнить).
class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_REMINDER_LATER) {
            intent.action = ACTION_SHOW
            val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            val triggerTime = System.currentTimeMillis() + 1 * 60 * 1000
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
            //при нажатии на кнопку "напомнить" нужно скрыть текущее
            NotificationUtils.cancelCurrent(context)
        } else if (intent.action == ACTION_SHOW) {
            NotificationUtils.showRemindedNotification(intent, context)
        }
    }

    companion object {
        const val ACTION_REMINDER_LATER = "remind_later"
        const val ACTION_SHOW = "action_show"
    }
}