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
        val builder = NotificationCompat.Builder(applicationContext, "channel_id")
            .setSmallIcon(com.coolkosta.core.R.mipmap.ic_launcher)
            .setContentTitle(event.title)
            .setContentText("Спасибо, что пожертвовали $amount ₽! Будем очень признательны, если вы сможете пожертвовать еще больше.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(getPendingIntent(event))
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(applicationContext)
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            notificationManager.notify(1, builder.build())
        }

        return Result.success()
    }

    private fun createNotificationChannel() {
        val name = "notification_name_channel"
        val descriptionText = "notification_description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("channel_id", name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }

    private fun getPendingIntent(event: EventEntity): PendingIntent? {
        val intent = Intent(applicationContext, NotificationReceiver::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.putExtra("event", event)

        return PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }
}