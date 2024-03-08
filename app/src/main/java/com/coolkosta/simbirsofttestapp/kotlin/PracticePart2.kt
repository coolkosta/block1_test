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

fun main() {

    /**
     * Создать объект класса User, вывести в лог startTime данного юзера,
     * после вызвать Thread.sleep(1000) и повторно вывести в лог startTime.
     */

    val user = User(id = 1, name = "Konstantin", age = 24, type = Type.DEMO)
    println(user.startTime)
    Thread.sleep(1000)
    println(user.startTime)

    /**
     * Создать список пользователей, содержащий в себе один объект класса User.
     * Используя функцию apply, добавить ещё несколько объектов класса User в список пользователей.
     */
    val usersList = mutableListOf(User(id = 0, name = "Stas", age = 14, type = Type.DEMO))

    usersList.apply {
        add(User(id = 2, name = "Igor", age = 34, type = Type.FULL))
        add(User(id = 1, name = "Vlad", age = 19, type = Type.DEMO))
        add(User(id = 3, name = "Ivan", age = 13, type = Type.FULL))
    }
}