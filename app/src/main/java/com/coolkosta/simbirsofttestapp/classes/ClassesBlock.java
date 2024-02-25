package com.coolkosta.simbirsofttestapp.classes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Набор заданий по работе с классами в java.
 * <p>
 * Задания подразумевают создание класса(ов), выполняющих задачу.
 * <p>
 * Проверка осуществляется ментором.
 */
public interface ClassesBlock {

    /*
      I

      Создать класс с двумя переменными. Добавить функцию вывода на экран
      и функцию изменения этих переменных. Добавить функцию, которая находит
      сумму значений этих переменных, и функцию, которая находит наибольшее
      значение из этих двух переменных.
     */

    class TwoVariables {
        private int a;
        private int b;

        TwoVariables(int a, int b) {
            this.a = a;
            this.b = b;
        }

        void displayNumbers() {
            System.out.printf("First number: %d, second number: %d\n", a, b);
        }

        void setNumbers(int a, int b) {
            this.a = a;
            this.b = b;
        }

        int getSum() {
            return a + b;
        }

        int getGreaterNumber() {
            return Math.max(a, b);
        }
    }

    /*
      II

      Создать класс, содержащий динамический массив и количество элементов в нем.
      Добавить конструктор, который выделяет память под заданное количество элементов.
      Добавить методы, позволяющие заполнять массив случайными числами,
      переставлять в данном массиве элементы в случайном порядке, находить количество
      различных элементов в массиве, выводить массив на экран.
     */

    class DynamicArray {
        private final ArrayList<Integer> arrayList = new ArrayList<>();
        private final int capacity;
        private final Random random = new Random();

        DynamicArray(int startCapacity) {
            this.arrayList.ensureCapacity(startCapacity);
            this.capacity = startCapacity;
        }

        ArrayList<Integer> getArrayList() {
            return arrayList;
        }

        void fillArrayRandomNumbers() {
            for (int i = 0; i < capacity; i++) {
                arrayList.add(i, random.nextInt(101));
            }
        }

        void shuffleArray() {
            for (int i = capacity - 1; i >= 0; i--) {
                int arrayRandomNumber = arrayList.get(random.nextInt(i + 1));
                int arrayRandomNumberIndex = arrayList.indexOf(arrayRandomNumber);
                int indexINumber = arrayList.get(i);
                arrayList.set(i, arrayRandomNumber);
                arrayList.set(arrayRandomNumberIndex, indexINumber);
            }
        }

        int countDistinctElements() {
            HashSet<Integer> distinctElements = new HashSet<>(arrayList);
            return distinctElements.size();
        }
    }

    /*
      III

      Описать класс, представляющий треугольник. Предусмотреть методы для создания объектов,
      вычисления площади, периметра и точки пересечения медиан.
      Описать свойства для получения состояния объекта.
     */

    class Triangle {
        private Point a;
        private Point b;
        private Point c;

        public Triangle(Point a, Point b, Point c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        public Point getA() {
            return a;
        }

        public Point getB() {
            return b;
        }

        public Point getC() {
            return c;
        }

        public void setA(Point point) {
            this.a = point;
        }

        public void setB(Point point) {
            this.b = point;
        }

        public void setC(Point point) {
            this.c = point;
        }

        public double square() {
            // формула Герона
            double ab = Point.distance(a, b);
            double bc = Point.distance(b, c);
            double ac = Point.distance(a, c);
            double p = (ab + bc + ac) / 2;
            return Math.sqrt(p * (p - ab) * (p - bc) * (p - ac));
        }

        public double perimeter() {
            double ab = Point.distance(a, b);
            double bc = Point.distance(b, c);
            double ac = Point.distance(a, c);
            return ab + bc + ac;
        }

        public Point medianCrossing() {
            return new Point((a.x + b.x + c.x) / 3, (a.y + b.y + c.y) / 3);
        }

        public static class Point {
            public double x, y;

            Point(double x, double y) {
                this.x = x;
                this.y = y;
            }

            Point() {
                this(0, 0);
            }

            static double distance(Point a, Point b) {
                double dx = a.x - b.x;
                double dy = a.y - b.y;
                return Math.sqrt(dx * dx + dy * dy);
            }
        }
    }

    /*
      IV

      Составить описание класса для представления времени.
      Предусмотреть возможности установки времени и изменения его отдельных полей
      (час, минута, секунда) с проверкой допустимости вводимых значений.
      В случае недопустимых значений полей выбрасываются исключения.
      Создать методы изменения времени на заданное количество часов, минут и секунд.
     */

    class Clock {
        private int hour;
        private int minute;
        private int second;

        Clock(int hour, int minute, int second) {
            try {
                if (hour < 0 || hour > 23 || minute < 0 || minute > 59 || second < 0 || second > 59) {
                    throw new InvalidTimeException(
                            "Введен неверный часовой формат. Время будет сброшено до 00:00:00"
                    );
                }
                this.hour = hour;
                this.minute = minute;
                this.second = second;
            } catch (InvalidTimeException ex) {
                System.out.println(ex.getMessage());
                this.hour = 0;
                this.minute = 0;
                this.second = 0;
            }
        }

        public String getCurrentTime() {
            return String.format(
                    Locale.getDefault(),
                    "%02d:%02d:%02d",
                    hour, minute, second
            );
        }

        void addHour(int hours) {
            try {
                if (hours < 0) {
                    throw new IllegalArgumentException("Количество часов должно быть положительным числом");
                }

                this.hour = (this.hour + hours) % 24;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }

        void addMinute(int minutes) {
            try {
                if (minutes < 0) {
                    throw new IllegalArgumentException(
                            "Количество минут должно быть положительным числом"
                    );
                }
                int totalMinutes = this.hour * 60 + this.minute + minutes;
                int newHour = (totalMinutes / 60) % 24;
                int newMinute = totalMinutes % 60;

                this.hour = newHour;
                this.minute = newMinute;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }

        void addSecond(int seconds) {
            try {
                if (seconds < 0) {
                    throw new IllegalArgumentException(
                            "Количество секунд должно быть положительным числом"
                    );
                }
                int totalSeconds = this.hour * 3600 + this.minute * 60 + this.second + seconds;
                int newHour = (totalSeconds / 3600) % 24;
                int newMinute = (totalSeconds / 60) % 60;
                int newSecond = totalSeconds % 60;

                this.hour = newHour;
                this.minute = newMinute;
                this.second = newSecond;
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
        }

        static class InvalidTimeException extends Exception {
            InvalidTimeException(String message) {
                super(message);
            }
        }
    }

    /*
      V

      Класс Абонент: Идентификационный номер, Фамилия, Имя, Отчество, Адрес,
      Номер кредитной карточки, Дебет, Кредит, Время междугородных и городских переговоров;
      Конструктор; Методы: установка значений атрибутов, получение значений атрибутов,
      вывод информации. Создать массив объектов данного класса.
      Вывести сведения относительно абонентов, у которых время городских переговоров
      превышает заданное.  Сведения относительно абонентов, которые пользовались
      междугородной связью. Список абонентов в алфавитном порядке.
     */

    class Subscriber {
        private int id;
        private String surname;
        private String name;
        private String middleName;
        private String address;
        private String cardNumber;
        private double debit;
        private double credit;
        private double localCallTime;
        private double internationalCallTime;

        Subscriber(
                int id,
                String surname,
                String name,
                String middleName,
                String address,
                String cardNumber,
                double debit,
                double credit,
                double localCallTime,
                double internationalCallTime
        ) {
            this.id = id;
            this.surname = surname;
            this.name = name;
            this.middleName = middleName;
            this.address = address;
            this.cardNumber = cardNumber;
            this.debit = debit;
            this.credit = credit;
            this.localCallTime = localCallTime;
            this.internationalCallTime = internationalCallTime;
        }

        void printInfo() {
            System.out.println("Идентификационный номер: " + id);
            System.out.println("Фамилия: " + surname);
            System.out.println("Имя: " + name);
            System.out.println("Отчество: " + middleName);
            System.out.println("Адрес: " + address);
            System.out.println("Номер кредитной карточки: " + cardNumber);
            System.out.println("Дебет: " + debit);
            System.out.println("Кредит: " + credit);
            System.out.println("Время городских переговоров: " + localCallTime);
            System.out.println("Время междугородных переговоров: " + internationalCallTime);
            System.out.println();
        }

        void setId(int id) {
            this.id = id;
        }

        void setSurname(String surname) {
            this.surname = surname;
        }

        void setName(String name) {
            this.name = name;
        }

        void setMiddleName(String middleName) {
            this.middleName = middleName;
        }

        void setAddress(String address) {
            this.address = address;
        }

        void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        void setDebit(double debit) {
            this.debit = debit;
        }

        void setCredit(double credit) {
            this.credit = credit;
        }

        void setLocalCallTime(double localCallTime) {
            this.localCallTime = localCallTime;
        }

        void setInternationalCallTime(double internationalCallTime) {
            this.internationalCallTime = internationalCallTime;
        }
    }

    /*
      VI

      Задача на взаимодействие между классами. Разработать систему «Вступительные экзамены».
      Абитуриент регистрируется на Факультет, сдает Экзамены. Преподаватель выставляет Оценку.
      Система подсчитывает средний бал и определяет Абитуриента, зачисленного в учебное заведение.
     */

    class Applicant {
        private String name;
        private List<Exam> exams = new ArrayList<>();

        public Applicant(String name) {
            this.name = name;
        }

        public void addExam(Exam exam) {
            exams.add(exam);
        }

        public List<Exam> getExams() {
            return exams;
        }

        public String getName() {
            return name;
        }
    }

    class Faculty {
        private String name;
        private List<Applicant> applicants = new ArrayList<>();

        public Faculty(String name) {
            this.name = name;
        }

        public void registerApplicant(Applicant applicant) {
            applicants.add(applicant);
        }

        public List<Applicant> getApplicants() {
            return applicants;
        }

        public String getName() {
            return name;
        }
    }

    class Exam {
        private String subject;
        private int score;

        public Exam(String subject, int score) {
            this.subject = subject;
            this.score = score;
        }

        public int getScore() {
            return score;
        }
    }

    class AdmissionSystem {
        public void admitStudents(Faculty faculty) {
            for (Applicant applicant : faculty.getApplicants()) {
                int totalScore = 0;
                for (Exam exam : applicant.getExams()) {
                    totalScore += exam.getScore();
                }
                double averageScore = (double) totalScore / applicant.getExams().size();
                if (averageScore >= 70) {
                    System.out.println(applicant.getName() + " зачислен на факультет " + faculty.getName());
                } else {
                    System.out.println(applicant.getName() + " не зачислен на факультет " + faculty.getName());
                }
            }
        }
    }

    class Professor {
        public void gradeExam(Exam exam, int score) {
            exam.score = score;
        }
    }
    /*
      VII

      Задача на взаимодействие между классами. Разработать систему «Интернет-магазин».
      Товаровед добавляет информацию о Товаре. Клиент делает и оплачивает Заказ на Товары.
      Товаровед регистрирует Продажу и может занести неплательщика в «черный список».
     */
    class Product {
        private String name;
        private double price;

        public Product(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }
    }

    class Customer {
        private String name;
        private String email;
        private String address;

        public Customer(String name, String email, String address) {
            this.name = name;
            this.email = email;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getAddress() {
            return address;
        }
    }

    class Order {
        private Customer customer;
        private List<Product> products;
        private boolean paid;

        public Order(Customer customer, List<Product> products) {
            this.customer = customer;
            this.products = products;
            this.paid = false;
        }

        public Customer getCustomer() {
            return customer;
        }

        public List<Product> getProducts() {
            return products;
        }

        public boolean isPaid() {
            return paid;
        }

        public void setPaid(boolean paid) {
            this.paid = paid;
        }
    }

    class Salesperson {
        private List<Order> orders;
        private List<Customer> blacklist;

        public Salesperson() {
            this.orders = new ArrayList<>();
            this.blacklist = new ArrayList<>();
        }

        public void registerSale(Order order) {
            if (order.isPaid()) {
                orders.add(order);
            } else {
                blacklist.add(order.getCustomer());
            }
        }

        public List<Order> getOrders() {
            return orders;
        }

        public List<Customer> getBlacklist() {
            return blacklist;
        }
    }
    class Blacklist {
        private List<Customer> customers;

        public Blacklist() {
            this.customers = new ArrayList<>();
        }

        public void addCustomer(Customer customer) {
            customers.add(customer);
        }

        public boolean isBlacklisted(Customer customer) {
            return customers.contains(customer);
        }
    }


}
