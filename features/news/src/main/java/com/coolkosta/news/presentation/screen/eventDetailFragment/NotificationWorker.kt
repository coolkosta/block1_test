package com.coolkosta.news.presentation.screen.eventDetailFragment

import android.Manifest
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
                .setContentIntent(getPendingIntent(event))
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

    private fun getPendingIntent(event: EventEntity): PendingIntent {
        val intent =
            (applicationContext as NewsComponentProvider).getNewsComponent().intentActivity()
                .getActivityIntent()
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra(NAME_EVENT_EXTRA, event)

        return PendingIntent.getActivity(
            applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
    }

    companion object {
        private const val ID_CHANNEL_DONATION_NOTIFICATION = "ID_CHANNEL_DONATION_NOTIFICATION"
        private const val ID_DONATION_NOTIFICATION = 1
        private const val NAME_CHANNEL_DONATION_NOTIFICATION = "donation notification"
        private const val DESCRIPTION_DONATION_NOTIFICATION = "notifications sent donations"

    }
}