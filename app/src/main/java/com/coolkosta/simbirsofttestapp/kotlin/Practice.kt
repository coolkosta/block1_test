package com.coolkosta.simbirsofttestapp.kotlin

/**
 * Необходимо создать интерфейс Publication, у которого должно быть свойства – price и wordCount,
 * а также метод getType, возвращающий строку. Создать два класса,
 * реализующих данный интерфейс – Book и Magazine.
 * В методе getType для класса Book возвращаем строку с зависимости от количества слов.
 * Если количество слов не превышает 1000, то вывести “Flash Fiction”, 7,500 –“Short Story”,
 * всё, что выше – “Novel”. Для класса Magazine возвращаем строку “Magazine”.
 */

interface Publication {
    val price: Int
    val wordCount: Int
    fun getType(): String
}

class Book(override val price: Int, override val wordCount: Int) : Publication {
    override fun getType(): String {
        if (wordCount < 1000) {
            return "Flash Fiction"
        } else if (wordCount == 7500) {
            return "Short Story"
        } else return "Novel"
    }
}

class Magazine(override val price: Int, override val wordCount: Int) : Publication {
    override fun getType(): String {
        return "Magazine"
    }
}