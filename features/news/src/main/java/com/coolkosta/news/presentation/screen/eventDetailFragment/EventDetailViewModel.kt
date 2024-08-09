package com.coolkosta.news.presentation.screen.eventDetailFragment


import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.coolkosta.news.R
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class EventDetailViewModel @Inject constructor(private val application: Application) :
    ViewModel() {

    private val _state = MutableStateFlow(EventState())
    val state = _state.asStateFlow()

    fun sendEvent(event: EventDetailsEvent) {
        when (event) {
            is EventDetailsEvent.CurrentEvent -> {
                _state.update {
                    it.copy(event = event.eventEntity)
                }
            }

            is EventDetailsEvent.DonationQueryChanged -> {
                _state.update {
                    it.copy(
                        currentAmount = event.donation,
                    )
                }
                updateButtonEnabled()
            }

            is EventDetailsEvent.DonationTransferred -> {
                when {
                    ContextCompat.checkSelfPermission(
                        application.applicationContext,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED -> {
                        Toast.makeText(
                            application.applicationContext,
                            application.getString(R.string.notification_denied_text),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {
                        eventNotification()
                    }
                }
            }
        }
    }

    private fun updateButtonEnabled() {
        val range = 1..9999999
        _state.update {
            it.copy(isEnabled = range.contains(_state.value.currentAmount))
        }
    }

    private fun eventNotification() {
        val jsonEventString = Gson().toJson(_state.value.event)
        val inputData = Data.Builder()
            .putString(KEY_EVENT_DATA, jsonEventString)
            .putInt(KEY_AMOUNT_DATA, _state.value.currentAmount)
            .build()

        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<NotificationWorker>()
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(application.applicationContext).enqueue(workRequest)

    }

    companion object {
        const val KEY_EVENT_DATA = "KEY_EVENT_DATA"
        const val KEY_AMOUNT_DATA = "KEY_AMOUNT_DATA"
    }

}