package com.coolkosta.simbirsofttestapp.data.source.remote.dto

import com.coolkosta.simbirsofttestapp.data.source.local.entity.EventEntity


data class EventDto(
    val id: String,
    val name: String,
    val startDate: Long,
    val endDate: Long,
    val description: String,
    val status: Long,
    val photos: List<String>,
    val category: List<String>,
    val createAt: Long,
    val phone: String,
    val address: String,
    val organisation: String
) {
    fun toEventEntity(): EventEntity {
        return EventEntity(
            id = id.toInt(),
            categoryIds = category.map { it.toInt() },
            foundation = organisation,
            title = name,
            description = description,
            date = "2024-04-20",
            location = address,
            contactInfo = phone,
            imageName = photos.first()

        )
    }
}


