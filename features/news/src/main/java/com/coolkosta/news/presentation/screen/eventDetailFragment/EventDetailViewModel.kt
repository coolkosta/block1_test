package com.coolkosta.news.presentation.screen.eventDetailFragment

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class EventDetailViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<EventState>(EventState.Loading)
    val state = _state.asStateFlow()

    fun sendEvent(event: EventDetailsEvent) {
        when (event) {
            is EventDetailsEvent.CurrentEvent -> {
                _state.update {
                    EventState.Success(event = event.eventEntity, donation = 0.00)
                }
            }

            is EventDetailsEvent.DonationTransferred -> {

            }
        }
    }

}