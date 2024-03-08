package com.coolkosta.simbirsofttestapp.kotlin

import java.util.Date

/**
 * Создать enum Type с константами DEMO и FULL.
 */
enum class Type {
    DEMO,
    FULL
}

/**
 * Реализовать класс данных User с полями id, name, age и type.
 * У класса User создать ленивое свойство startTime, в котором получаем текущее время.
 */

data class User(val id: Int, val name: String, val age: Int, val type: Type) {
    val startTime: Date by lazy {
        Date()
    }
}