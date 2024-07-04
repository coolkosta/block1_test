package com.coolkosta.simbirsofttestapp.data.source.remote.dto


import com.coolkosta.simbirsofttestapp.data.source.local.entity.CategoryEntity
import com.google.gson.annotations.SerializedName

data class CategoryDto(
    val id: String,
    @SerializedName("name_en")
    val nameEn: String,
    val name: String,
    val image: String
) {
    fun toCategoryEntity(): CategoryEntity {
        return CategoryEntity(
            id = id.toInt(),
            title = name
        )
    }
}
