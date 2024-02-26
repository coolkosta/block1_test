package com.coolkosta.simbirsofttestapp.training;

interface Shape {
    double perimeter();
    double area();
}

class Rectangle implements Shape {
    private double width;
    private double length;

    public Rectangle(double width, double length) {
        this.width = width;
        this.length = length;
    }

    public double perimeter() {
        return 2 * (width + length);
    }

    public double area() {
        return width * length;
    }
}

class Square implements Shape {
    private double sideLength;

    public Square(double sideLength) {
        this.sideLength = sideLength;
    }

    public double perimeter() {
        return 4 * sideLength;
    }

    public double area() {
        return sideLength * sideLength;
    }
}

class Circle implements Shape {
    private double diameter;

    public Circle(double diameter) {
        this.diameter = diameter;
    }

    public double perimeter() {
        return Math.PI * diameter;
    }

    public double area() {
        return Math.PI * Math.pow(diameter / 2, 2);
    }
}

class ShapePractice {
    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle(5, 10);
        System.out.println("Rectangle Perimeter: " + rectangle.perimeter());
        System.out.println("Rectangle Area: " + rectangle.area());

        Square square = new Square(7);
        System.out.println("Square Perimeter: " + square.perimeter());
        System.out.println("Square Area: " + square.area());

        Circle circle = new Circle(10);
        System.out.println("Circle Perimeter: " + circle.perimeter());
        System.out.println("Circle Area: " + circle.area());
    }
}