package com.coolkosta.news.data.mapper

import com.coolkosta.news.data.source.local.model.EventDbModel
import com.coolkosta.news.data.source.remote.model.EventResponse
import org.junit.Assert.assertEquals
import org.junit.Test


class EventMapperTest{
    @Test
    fun testFromEventResponseToEventDbModel() {
        val eventResponse = EventResponse(
            id = "1",
            category = listOf("1", "2"),
            organisation = "Foundation",
            name = "Event",
            description = "Description",
            address = "Location",
            phone = "1234567890",
            photos = listOf("photo1.jpg", "photo2.jpg"),
            startDate = 1622515200000,
            endDate = 1622522400000,
            status = 1,
            createAt = 1622500000000
        )

        val eventDbModel = EventMapper.fromEventResponseToEventDbModel(eventResponse)

        assertEquals(1, eventDbModel.id)
        assertEquals(listOf(1, 2), eventDbModel.categoryIds)
        assertEquals("Foundation", eventDbModel.foundation)
        assertEquals("Event", eventDbModel.title)
        assertEquals("Description", eventDbModel.description)
        assertEquals("2024-04-20", eventDbModel.date)
        assertEquals("Location", eventDbModel.location)
        assertEquals("1234567890", eventDbModel.contactInfo)
        assertEquals("photo1.jpg", eventDbModel.imageName)
    }

    @Test
    fun testFromEventDbModelToEvent() {
        val eventDbModel = EventDbModel(
            id = 1,
            categoryIds = listOf(1, 2),
            foundation = "Foundation",
            title = "Event",
            description = "Description",
            date = "2024-04-20",
            location = "Location",
            contactInfo = "1234567890",
            imageName = "photo1.jpg"
        )

        val eventEntity = EventMapper.fromEventDbModelToEvent(eventDbModel)

        assertEquals(1, eventEntity.id)
        assertEquals(listOf(1, 2), eventEntity.categoryIds)
        assertEquals("Foundation", eventEntity.foundation)
        assertEquals("Event", eventEntity.title)
        assertEquals("Description", eventEntity.description)
        assertEquals("2024-04-20", eventEntity.date)
        assertEquals("Location", eventEntity.location)
        assertEquals("1234567890", eventEntity.contactInfo)
        assertEquals("photo1.jpg", eventEntity.imageName)
    }
}