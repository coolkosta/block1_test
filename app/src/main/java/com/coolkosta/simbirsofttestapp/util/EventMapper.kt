package com.coolkosta.simbirsofttestapp.util

import com.coolkosta.simbirsofttestapp.entity.EventEntity
import com.coolkosta.simbirsofttestapp.entity.Event

object EventMapper {
    fun fromRemoteEventToEvent(event: Event): EventEntity {
        return EventEntity(
            id = event.id.toInt(),
            categoryIds = event.category.map { it.toInt() },
            foundation = event.organisation,
            title = event.name,
            description = event.description,
            date = "2024-04-20",
            location = event.address,
            contactInfo = event.phone,
            imageName = event.photos.first()
        )
    }
}