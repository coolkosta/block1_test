package com.coolkosta.simbirsofttestapp.domain.repository

import com.coolkosta.simbirsofttestapp.domain.model.Event

fun interface EventRepository {
    suspend fun getEvents(): List<Event>
}