package com.coolkosta.simbirsofttestapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EventEntity(
    val id: Int,
    val categoryIds: List<Int>,
    val foundation: String,
    val title: String,
    val description: String,
    val date: String,
    val location: String,
    val contactInfo: String,
    val imageName: String,
) : Parcelable