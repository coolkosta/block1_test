package com.coolkosta.news.presentation.screen.eventDetailFragment

import android.Manifest
import android.app.Notification.EXTRA_NOTIFICATION_ID
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.IntentCompat
import com.coolkosta.core.util.Constants.NAME_EVENT_EXTRA
import com.coolkosta.news.di.NewsComponentProvider
import com.coolkosta.news.domain.model.EventEntity
import com.coolkosta.news.presentation.screen.eventDetailFragment.NotificationWorker.Companion.ID_DONATION_NOTIFICATION


class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == NotificationWorker.ACTION_REMINDER_LATER) {
            val event = IntentCompat.getParcelableExtra(
                intent,
                EXTRA_NOTIFICATION_ID,
                EventEntity::class.java
            )
            val builder = NotificationCompat.Builder(
                context,
                NotificationWorker.ID_CHANNEL_DONATION_NOTIFICATION
            )
                .setSmallIcon(com.coolkosta.core.R.drawable.ic_coins)
                .setContentTitle(event?.title)
                .setContentText("Напоминаем, что мы будем очень признательны, если вы сможете пожертвовать еще больше.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(openDetailScreenPendingIntent(context, event!!))
                .setAutoCancel(true)

            val notificationManager = NotificationManagerCompat.from(context)
            if (ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notificationManager.notify(ID_DONATION_NOTIFICATION, builder.build())
            }
        }

    }

    private fun openDetailScreenPendingIntent(context: Context, event: EventEntity): PendingIntent {
        val intent = (context.applicationContext as NewsComponentProvider).getNewsComponent()
            .intentActivity()
            .getActivityIntent()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(NAME_EVENT_EXTRA, event)

        return PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
    }

}