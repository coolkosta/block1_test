package com.coolkosta.simbirsofttestapp.util

import com.coolkosta.simbirsofttestapp.entity.Event
import com.coolkosta.simbirsofttestapp.entity.RemoteEvent

object EventMapper {
    fun fromRemoteEventToEvent(remoteEvent: RemoteEvent): Event {
        return Event(
            id = remoteEvent.id.toInt(),
            categoryIds = remoteEvent.category.map { it.toInt() },
            foundation = remoteEvent.organisation,
            title = remoteEvent.name,
            description = remoteEvent.description,
            date = "2024-04-20",
            location = remoteEvent.address,
            contactInfo = remoteEvent.phone,
            imageName = remoteEvent.photos.first()
        )
    }
}