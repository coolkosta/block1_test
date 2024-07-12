package com.coolkosta.simbirsofttestapp.data.source.remote.model

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    val id: String,
    @SerializedName("name_en")
    val nameEn: String,
    val name: String,
    val image: String
)