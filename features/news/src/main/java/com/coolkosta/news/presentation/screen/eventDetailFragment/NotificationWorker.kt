package com.coolkosta.news.presentation.screen.eventDetailFragment

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.coolkosta.news.domain.model.EventEntity
import com.coolkosta.news.presentation.screen.eventDetailFragment.EventDetailViewModel.Companion.KEY_AMOUNT_DATA
import com.coolkosta.news.presentation.screen.eventDetailFragment.EventDetailViewModel.Companion.KEY_EVENT_DATA
import com.google.gson.Gson

class NotificationWorker(private val context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {

        val eventDataString = inputData.getString(KEY_EVENT_DATA)
        val event: EventEntity = Gson().fromJson(eventDataString, EventEntity::class.java)
        val amount = inputData.getInt(KEY_AMOUNT_DATA, 0)

        NotificationUtils.showNotification(context, amount, event)
        return Result.success()
    }
}