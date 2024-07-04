package com.coolkosta.simbirsofttestapp.other.training;

import java.util.Arrays;

/**
 * Набор тренингов по работе со строками в java.
 * <p>
 * Задания определены в комментариях методов.
 * <p>
 * Проверка может быть осуществлена запуском тестов.
 * <p>
 * Доступна проверка тестированием @see StringsTrainingTest.
 */
public class StringsTraining {

    /**
     * Метод по созданию строки,
     * состоящей из нечетных символов
     * входной строки в том же порядке
     * (нумерация символов идет с нуля)
     *
     * @param text строка для выборки
     * @return новая строка из нечетных
     * элементов строки text
     */
    public String getOddCharacterString(String text) {
        StringBuilder charBuff = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (i % 2 != 0) {
                charBuff.append(ch);
            }
        }
        return charBuff.toString();
    }

    /**
     * Метод для определения количества
     * символов, идентичных последнему
     * в данной строке
     *
     * @param text строка для выборки
     * @return массив с номерами символов,
     * идентичных последнему. Если таких нет,
     * вернуть пустой массив
     */
    public int[] getArrayLastSymbol(String text) {
        if (text.isEmpty()) {
            return new int[0];
        }
        char lastChar = text.charAt(text.length() - 1);
        int count = 0;
        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) == lastChar) {
                count++;
            }
        }

        int[] result = new int[count];
        int index = 0;

        for (int i = 0; i < text.length() - 1; i++) {
            if (text.charAt(i) == lastChar) {
                result[index] = i;
                index++;
            }
        }
        return result;
    }

    /**
     * Метод по получению количества
     * цифр в строке
     *
     * @param text строка для выборки
     * @return количество цифр в строке
     */
    public int getNumbersCount(String text) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    /**
     * Дан текст. Заменить все цифры
     * соответствующими словами.
     *
     * @param text текст для поиска и замены
     * @return текст, где цифры заменены словами
     */
    public String replaceAllNumbers(String text) {
        text = text.replaceAll("0", "zero")
                .replaceAll("1", "one")
                .replaceAll("2", "two")
                .replaceAll("3", "three")
                .replaceAll("4", "four")
                .replaceAll("5", "five")
                .replaceAll("6", "six")
                .replaceAll("7", "seven")
                .replaceAll("8", "eight")
                .replaceAll("9", "nine");
        return text;
    }

    /**
     * Метод должен заменить заглавные буквы
     * на прописные, а прописные на заглавные
     *
     * @param text строка для изменения
     * @return измененная строка
     */
    public String capitalReverse(String text) {
        StringBuilder buffer = new StringBuilder(text);
        for (int i = 0; i < buffer.length() - 1; i++) {
            char ch = buffer.charAt(i);
            if (Character.isUpperCase(ch)) {
                buffer.setCharAt(i, Character.toLowerCase(ch));
            } else if (Character.isLowerCase(ch)) {
                buffer.setCharAt(i, Character.toUpperCase(ch));
            }
        }
        return buffer.toString();
    }

}
