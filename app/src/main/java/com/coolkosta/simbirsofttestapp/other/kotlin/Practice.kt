package com.coolkosta.simbirsofttestapp.other.kotlin

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
        return when {
            wordCount < 1000 -> "Flash Fiction"
            wordCount < 7500 -> "Short Story"
            else -> "Novel"
        }
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

    fun printPublicationInfo(publication: Publication) {
        println("${publication::class.simpleName} type: ${publication.getType()}, word count: ${publication.wordCount}, price: ${publication.price}€")
    }

    printPublicationInfo(shortStoryBook)
    printPublicationInfo(novelBook)
    printPublicationInfo(magazine)

    println(shortStoryBook === novelBook)
    println(shortStoryBook == novelBook)

    val fullNullPublication: Book? = null
    val notNullPublication: Book? = Book(price = 45, wordCount = 6000)

    notNullPublication?.let { buy(it) }
    fullNullPublication?.let { buy(it) }
    sum(45, 66)
    sum(33, 67)
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

/**
 * Создать переменную sum и присвоить ей лямбда-выражение,
 * которое будет складывать два переданных ей числа и выводить результат в лог.
 * Вызвать данное лямбда-выражение с произвольными параметрами.
 */
val sum: (Int, Int) -> Unit = { a, b ->
    println("Результат сложения: ${a + b}")
}

