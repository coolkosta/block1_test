package com.coolkosta.news.util

import com.coolkosta.news.domain.model.CategoryEntity
import com.coolkosta.news.domain.model.EventEntity

object SampleData {
    val events = listOf(
        EventEntity(
            id = 1,
            categoryIds = listOf(1, 2),
            foundation = "Фонд 'Подари жизнь'",
            title = "Благотворительный концерт",
            description = "Приглашаем всех на благотворительный концерт. Все собранные средства пойдут на лечение детей.",
            date = "2024-04-20",
            location = "Москва, Крокус Сити Холл",
            contactInfo = "+7 (999) 354-34-24",
            imageName = "image_child"

        ),
        EventEntity(
            id = 2,
            categoryIds = listOf(2),
            foundation = "Фонд 'Нужна помощь'",
            title = "Сбор средств для детей",
            description = "Организуем сбор средств для помощи детям с редкими заболеваниями. Каждый рубль на счету!",
            date = "2024-05-29",
            location = "Санкт-Петербург, Дворцовая площадь",
            contactInfo = "+7 (999) 354-34-24",
            imageName = "image_kid"
        )
    )
    val categories = listOf(
        CategoryEntity(
            id = 1,
            title = "Благотворительность"
        ),
        CategoryEntity(
            id = 2,
            title = "Дети"
        )
    )
}