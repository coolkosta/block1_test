package com.coolkosta.simbirsofttestapp.entity

data class RemoteEvent(
    val id: String,
    val name: String,
    val startDate: Long,
    val endDate: Long,
    val description: String,
    val status: Long,
    val photos: List<String>,
    val category: List<String>,
    val createAt: Long,
    val phone: String,
    val address: String,
    val organisation: String
)
