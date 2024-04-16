package com.coolkosta.simbirsofttestapp.util

import com.google.gson.annotations.SerializedName


data class Event(
    val id: Int,
    @SerializedName("category_ids")
    val categoryIds: List<Int>,
    val foundation: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    @SerializedName("contact_info")
    val contactInfo: String,
    @SerializedName("image_name")
    val imageName: String,
)
