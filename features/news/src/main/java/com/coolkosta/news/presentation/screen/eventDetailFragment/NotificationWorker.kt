package com.coolkosta.news.presentation.screen.eventDetailFragment

import android.Manifest
import android.app.AlarmManager
import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.coolkosta.core.util.Constants.NAME_EVENT_EXTRA
import com.coolkosta.news.R.string.notification_description_tex
import com.coolkosta.news.di.NewsComponentProvider
import com.coolkosta.news.domain.model.EventEntity
import com.coolkosta.news.presentation.screen.eventDetailFragment.EventDetailViewModel.Companion.KEY_AMOUNT_DATA
import com.coolkosta.news.presentation.screen.eventDetailFragment.EventDetailViewModel.Companion.KEY_EVENT_DATA
import com.google.gson.Gson

class NotificationWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {

        val eventDataString = inputData.getString(KEY_EVENT_DATA)
        val event = Gson().fromJson(eventDataString, EventEntity::class.java)
        val amount = inputData.getInt(KEY_AMOUNT_DATA, 0)

        createNotificationChannel()
        val builder =
            NotificationCompat.Builder(applicationContext, ID_CHANNEL_DONATION_NOTIFICATION)
                .setSmallIcon(com.coolkosta.core.R.drawable.ic_coins).setContentTitle(event.title)
                .setContentText(applicationContext.getString(notification_description_tex, amount))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(openDetailScreenPendingIntent(event))
                .addAction(
                    com.coolkosta.core.R.drawable.ic_history,
                    "Напомнить позже",
                    setReminderLater(event)
                )
                .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        if (ActivityCompat.checkSelfPermission(
                applicationContext, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(ID_DONATION_NOTIFICATION, builder.build())
        }

        return Result.success()
    }

    private fun createNotificationChannel() {
        val name = NAME_CHANNEL_DONATION_NOTIFICATION
        val descriptionText = DESCRIPTION_DONATION_NOTIFICATION
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel =
            NotificationChannel(ID_CHANNEL_DONATION_NOTIFICATION, name, importance).apply {
                description = descriptionText
            }

        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }

    private fun openDetailScreenPendingIntent(event: EventEntity): PendingIntent {
        val intent =
            (applicationContext as NewsComponentProvider).getNewsComponent().intentActivity()
                .getActivityIntent()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(NAME_EVENT_EXTRA, event)

        return PendingIntent.getActivity(
            applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun setReminderLater(event: EventEntity): PendingIntent {
        val reminderIntent = Intent(applicationContext, NotificationReceiver::class.java).apply {
            action = ACTION_REMINDER_LATER
            putExtra(EXTRA_NOTIFICATION_ID, event)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            0,
            reminderIntent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val alarmManager =
            applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val triggerTime = System.currentTimeMillis() + 1 * 60 * 1000
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
        return pendingIntent
    }

    companion object {
        const val ID_CHANNEL_DONATION_NOTIFICATION = "ID_CHANNEL_DONATION_NOTIFICATION"
        const val ID_DONATION_NOTIFICATION = 1
        private const val NAME_CHANNEL_DONATION_NOTIFICATION = "donation notification"
        private const val DESCRIPTION_DONATION_NOTIFICATION = "notifications sent donations"
        const val ACTION_REMINDER_LATER = "remind_later"
    }
}