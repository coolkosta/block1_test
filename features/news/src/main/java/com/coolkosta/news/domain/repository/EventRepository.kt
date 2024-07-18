package com.coolkosta.news.domain.repository

import com.coolkosta.news.domain.model.EventEntity

fun interface EventRepository {
    suspend fun getEvents(): List<EventEntity>
}