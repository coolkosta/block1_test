package com.coolkosta.simbirsofttestapp.domain.repository

import com.coolkosta.simbirsofttestapp.domain.model.Category

fun interface CategoryRepository {
    suspend fun getCategories(): List<Category>
}