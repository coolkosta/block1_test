package com.coolkosta.news.presentation.screen.eventDetailFragment

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.IntentCompat
import com.coolkosta.core.util.Constants
import com.coolkosta.core.util.Constants.EVENT_EXTRA
import com.coolkosta.news.R
import com.coolkosta.news.di.NewsComponentProvider
import com.coolkosta.news.domain.model.EventEntity

class NotificationUtils {

    companion object {
        fun showNotification(context: Context, amount: Int, event: EventEntity) {
            createNotificationChannel(context)

            val reminderIntent = Intent(context, NotificationReceiver::class.java).apply {
                action = NotificationReceiver.ACTION_REMINDER_LATER
                putExtra(SNOOZE_EVENT_EXTRA, event)
            }

            val builder =
                NotificationCompat.Builder(context, ID_CHANNEL_DONATION_NOTIFICATION)
                    .setSmallIcon(com.coolkosta.core.R.drawable.ic_coins)
                    .setContentTitle(event.title)
                    .setContentText(
                        context.getString(
                            R.string.notification_description_tex,
                            amount
                        )
                    )
                    .setStyle(
                        NotificationCompat.BigTextStyle()
                            .bigText(
                                context.getString(
                                    R.string.notification_description_tex,
                                    amount
                                )
                            )
                    )
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(openDetailScreenPendingIntent(context, event))
                    .addAction(
                        com.coolkosta.core.R.drawable.ic_history,
                        context.getString(R.string.remind_later_text),
                        PendingIntent.getBroadcast(
                            context,
                            0,
                            reminderIntent,
                            PendingIntent.FLAG_IMMUTABLE
                        )
                    )
                    .setAutoCancel(true)

            val notificationManager = NotificationManagerCompat.from(context)
            if (ActivityCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notificationManager.notify(ID_DONATION_NOTIFICATION, builder.build())
            }
        }

        fun cancelCurrent(context: Context) {
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.cancel(ID_DONATION_NOTIFICATION)
        }

        fun showRemindedNotification(intent: Intent, context: Context) {
            IntentCompat.getParcelableExtra(
                intent,
                SNOOZE_EVENT_EXTRA,
                EventEntity::class.java
            )?.let {
                val builder = NotificationCompat.Builder(
                    context,
                    ID_CHANNEL_DONATION_NOTIFICATION
                )
                    .setSmallIcon(com.coolkosta.core.R.drawable.ic_coins)
                    .setContentTitle(it.title)
                    .setContentText(context.getString(R.string.snooze_notification_description_text))
                    .setStyle(
                        NotificationCompat.BigTextStyle()
                            .bigText(context.getString(R.string.snooze_notification_description_text))
                    )
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(openDetailScreenPendingIntent(context, it))
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

        private fun createNotificationChannel(context: Context) {
            val name = NAME_CHANNEL_DONATION_NOTIFICATION
            val descriptionText = DESCRIPTION_DONATION_NOTIFICATION
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel =
                NotificationChannel(ID_CHANNEL_DONATION_NOTIFICATION, name, importance).apply {
                    description = descriptionText
                }

            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        private fun openDetailScreenPendingIntent(
            context: Context,
            event: EventEntity,
        ): PendingIntent {
            val intent = (context.applicationContext as NewsComponentProvider).getNewsComponent()
                .activityIntent().getActivityIntent()
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.putExtra(EVENT_EXTRA, event)

            Log.d("NotificationTest", "Send data from Notification Utils: $event")

            return PendingIntent.getActivity(
                context, 0, intent, PendingIntent.FLAG_IMMUTABLE
            )
        }

        private const val ID_CHANNEL_DONATION_NOTIFICATION = "ID_CHANNEL_DONATION_NOTIFICATION"
        private const val ID_DONATION_NOTIFICATION = 1
        private const val NAME_CHANNEL_DONATION_NOTIFICATION = "donation notification"
        private const val DESCRIPTION_DONATION_NOTIFICATION = "notifications sent donations"
        private const val SNOOZE_EVENT_EXTRA = "SNOOZE_EVENT_EXTRA"
    }
}