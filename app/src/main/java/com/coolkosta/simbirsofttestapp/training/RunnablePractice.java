package com.coolkosta.simbirsofttestapp.training;

public class RunnablePractice {
    public static void main(String[] args) {
        // Лямбда-выражение для вывода фразы "I love Java"
        Runnable myClosure = () -> System.out.println("I love Java");

        // Вызов лямбда-выражения
        myClosure.run();

        // Функция для запуска лямбда-выражения заданное количество раз
        repeatTask(10, myClosure);
    }

    public static void repeatTask(int times, Runnable task) {
        for (int i = 0; i < times; i++) {
            task.run();
        }
    }
}
