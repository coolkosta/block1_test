package com.coolkosta.simbirsofttestapp.other.training;

class DirectionPractice {
    public enum Directions {
        UP, DOWN, LEFT, RIGHT
    }

    public static void main(String[] args) {
        // Начальные координаты
        int x = 0;
        int y = 0;

        // Массив направлений
        Directions[] directions = {Directions.UP, Directions.UP, Directions.LEFT, Directions.DOWN, Directions.LEFT, Directions.DOWN, Directions.DOWN, Directions.RIGHT, Directions.RIGHT, Directions.DOWN, Directions.RIGHT};

        // Выполнение последовательности шагов
        for (Directions direction : directions) {
            // Вычисление новых координат после перехода
            int[] newCoordinates = move(x, y, direction);
            x = newCoordinates[0];
            y = newCoordinates[1];

            // Вывод координат после каждого перехода
            System.out.println("New coordinates: (" + x + ", " + y + ")");
        }
    }

    public static int[] move(int x, int y, Directions direction) {
        switch (direction) {
            case UP:
                y++;
                break;
            case DOWN:
                y--;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
        }
        return new int[]{x, y};
    }
}
