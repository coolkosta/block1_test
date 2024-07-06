package com.coolkosta.simbirsofttestapp.data.mapper

import com.coolkosta.simbirsofttestapp.data.source.local.model.EventDbModel
import com.coolkosta.simbirsofttestapp.data.source.remote.model.EventResponse
import com.coolkosta.simbirsofttestapp.domain.model.Event

object EventMapper {
    fun fromEventResponseToEventDbModel(eventResponse: EventResponse): EventDbModel {
        return EventDbModel(
            id = eventResponse.id.toInt(),
            categoryIds = eventResponse.category.map { it.toInt() },
            foundation = eventResponse.organisation,
            title = eventResponse.name,
            description = eventResponse.description,
            date = "2024-04-20",
            location = eventResponse.address,
            contactInfo = eventResponse.phone,
            imageName = eventResponse.photos.first()
        )
    }

    fun fromEventDbModelToEvent(eventDbModel: EventDbModel): Event {
        return Event(
            id = eventDbModel.id,
            categoryIds = eventDbModel.categoryIds,
            foundation = eventDbModel.foundation,
            title = eventDbModel.title,
            description = eventDbModel.description,
            date = eventDbModel.date,
            location = eventDbModel.location,
            contactInfo = eventDbModel.contactInfo,
            imageName = eventDbModel.imageName
        )
    }
}