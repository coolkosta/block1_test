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
        } else if (wordCount < 7500) {
            return "Short Story"
        } else return "Novel"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        if (price != other.price) return false
        if (wordCount != other.wordCount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = price
        result = 31 * result + wordCount
        return result
    }
}

class Magazine(override val price: Int, override val wordCount: Int) : Publication {
    override fun getType(): String {
        return "Magazine"
    }
}

/**
 * Создать два объекта класса Book и один объект Magazine.
 * Вывести в лог для каждого объекта тип, количество строк и цену в евро в отформатированном виде.
 * У класса Book переопределить метод equals и произвести сравнение сначала по ссылке,
 * затем используя метод equals. Результаты сравнений вывести в лог.
 */

fun main() {
    val shortStoryBook = Book(price = 45, wordCount = 3000)
    val novelBook = Book(price = 150, wordCount = 8000)
    val magazine = Magazine(price = 20, wordCount = 1500)

    println("shortStoryBook type: ${shortStoryBook.getType()}, word count: ${shortStoryBook.wordCount}, price: ${shortStoryBook.price}€")
    println("novelBook type: ${novelBook.getType()}, word count: ${novelBook.wordCount}, price: ${novelBook.price}€")
    println("magazine type: ${magazine.getType()}, word count: ${magazine.wordCount}, price: ${magazine.price}€")
    println(shortStoryBook === novelBook)
    println(shortStoryBook.equals(novelBook))

    val fullNullPublication: Book? = null
    val notNullPublication: Book? = Book(price = 45, wordCount = 6000)

    notNullPublication.let {
        if (it != null) {
            buy(it)
        }
    }
    fullNullPublication.let { buy(it!!) }
}

/**
 * Создать метод buy, который в качестве параметра принимает Publication (notnull - значения)
 * и выводит в лог “The purchase is complete. The purchase amount was [цена издания]”.
 * Создать две переменных класса Book, в которых могут находиться null значения.
 * Присвоить одной null, а второй любое notnull значение.
 * Используя функцию let, попробуйте вызвать метод buy с каждой из переменных.
 */
fun buy(publication: Publication) {
    println("The purchase is complete. The purchase amount was ${publication.price}€")
}
