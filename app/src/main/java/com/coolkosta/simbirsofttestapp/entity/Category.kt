package com.coolkosta.simbirsofttestapp.entity

import com.google.gson.annotations.SerializedName

data class Category(
    val id: String,
    @SerializedName("name_en")
    val nameEn: String,
    val name: String,
    val image: String
)
