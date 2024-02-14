package com.coolkosta.simbirsofttestapp.training;

/**
 * Набор тренингов по работе с примитивными типами java.
 * <p>
 * Задания определены в комментариях методов.
 * <p>
 * Проверка может быть осуществлена запуском тестов.
 * <p>
 * Доступна проверка тестированием @see ElementaryTrainingTest.
 */
public class ElementaryTraining {

    /**
     * Метод должен возвращать среднее значение
     * для введенных параметров
     *
     * @param firstValue  первый элемент
     * @param secondValue второй элемент
     * @return среднее значение для введенных чисел
     */
    public double averageValue(int firstValue, int secondValue) {
        return (firstValue + secondValue) / 2.0;
    }

    /**
     * Пользователь вводит три числа.
     * Произвести манипуляции и вернуть сумму новых чисел
     *
     * @param firstValue  увеличить в два раза
     * @param secondValue уменьшить на три
     * @param thirdValue  возвести в квадрат
     * @return сумма новых трех чисел
     */
    public double complicatedAmount(int firstValue, int secondValue, int thirdValue) {
        int firstNewValue = firstValue * 2;
        int secondNewValue = secondValue - 3;
        int thirdNewValue = thirdValue * thirdValue;
        return firstNewValue + secondNewValue + thirdNewValue;
    }

    /**
     * Метод должен поменять значение в соответствии с условием.
     * Если значение больше 3, то увеличить
     * на 10, иначе уменьшить на 10.
     *
     * @param value число для изменения
     * @return новое значение
     */
    public int changeValue(int value) {
        if (value > 3) {
            return value + 10;
        } else {
            return value - 10;
        }
    }

    /**
     * Метод должен менять местами первую
     * и последнюю цифру числа.
     * Обрабатывать максимум пятизначное число.
     * Если число < 10, вернуть
     * то же число
     *
     * @param value число для перестановки
     * @return новое число
     */
    public int swapNumbers(int value) {
        if (value < 10) {
            return value;
        }
        if (value < 100) {
            int lastDigit = value % 10;
            int firstDigit = value / 10;
            return lastDigit * 10 + firstDigit;
        }
        int lastDigit = value % 10;
        int firstDigit = value / 10000;
        int middleDigit = (value / 10) % 1000;
        return lastDigit * 10000 + middleDigit * 10 + firstDigit;
    }

    /**
     * Изменить значение четных цифр числа на ноль.
     * Счет начинать с единицы.
     * Обрабатывать максимум пятизначное число.
     * Если число < 10 вернуть
     * то же число.
     *
     * @param value число для изменения
     * @return новое число
     */
    public int zeroEvenNumber(int value) {
        if (value < 10) {
            return value;
        }
        int result = 0;
        int multiplier = 1;

        while (value > 0) {
            int digit = value % 10;
            if (digit % 2 == 0) {
                digit = 0;
            }

            result += digit*multiplier;
            multiplier *=10;
            value/=10;
        }
        return result;
    }
}
