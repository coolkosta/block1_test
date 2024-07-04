package com.coolkosta.simbirsofttestapp.util

import com.coolkosta.simbirsofttestapp.data.source.local.entity.EventEntity
import com.coolkosta.simbirsofttestapp.data.source.remote.dto.EventDto

object EventMapper {
    fun fromRemoteEventToEvent(eventDto: EventDto): EventEntity {
        return EventEntity(
            id = eventDto.id.toInt(),
            categoryIds = eventDto.category.map { it.toInt() },
            foundation = eventDto.organisation,
            title = eventDto.name,
            description = eventDto.description,
            date = "2024-04-20",
            location = eventDto.address,
            contactInfo = eventDto.phone,
            imageName = eventDto.photos.first()
        )
    }
}