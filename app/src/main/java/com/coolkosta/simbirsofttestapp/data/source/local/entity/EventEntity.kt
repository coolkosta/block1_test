package com.coolkosta.simbirsofttestapp.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.coolkosta.simbirsofttestapp.domain.model.Event
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "event_entity")
@TypeConverters(CategoryIdsConverter::class)
data class EventEntity(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "category_ids")
    val categoryIds: List<Int>,
    val foundation: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    @ColumnInfo(name = "contact_info")
    val contactInfo: String,
    @ColumnInfo(name = "image_name")
    val imageName: String,
) : Parcelable {
    fun toEvent(): Event {
        return Event(
            id = id.toInt(),
            categoryIds = categoryIds,
            foundation = foundation,
            title = title,
            description = description,
            date = "2024-04-20",
            location = location,
            contactInfo = contactInfo,
            imageName = imageName

        )
    }
}

class CategoryIdsConverter {
    @TypeConverter
    fun fromCategoryIdsList(categoryIds: List<Int>): String {
        return categoryIds.joinToString(separator = ",")
    }

    @TypeConverter
    fun toCategoryIdsList(categoryIdsString: String): List<Int> {
        return categoryIdsString.split(",").map { it.toInt() }
    }
}