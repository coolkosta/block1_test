package com.coolkosta.simbirsofttestapp.domain.repository

import com.coolkosta.simbirsofttestapp.domain.model.EventEntity

fun interface EventRepository {
    suspend fun getEvents(): List<EventEntity>
}