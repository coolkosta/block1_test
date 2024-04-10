package com.coolkosta.simbirsofttestapp.util

import androidx.annotation.DrawableRes

data class NewsItem(@DrawableRes val imageId: Int,  val title: String, val description: String, val dateTime: String)
