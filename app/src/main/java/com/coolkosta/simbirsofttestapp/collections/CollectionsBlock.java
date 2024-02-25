package com.coolkosta.simbirsofttestapp.collections;


import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

/**
 * Набор тренингов по работе со строками в java.
 * <p>
 * Задания определены в комментариях методов.
 * <p>
 * Проверка может быть осуществлена запуском тестов.
 * <p>
 * Доступна проверка тестированием @see CollectionsBlockTest.
 */
public class CollectionsBlock<T extends Comparable> {

    /**
     * Даны два упорядоченных по убыванию списка.
     * Объедините их в новый упорядоченный по убыванию список.
     * Исходные данные не проверяются на упорядоченность в рамках данного задания
     *
     * @param firstList  первый упорядоченный по убыванию список
     * @param secondList второй упорядоченный по убыванию список
     * @return объединенный упорядоченный список
     * @throws NullPointerException если один из параметров null
     */
    public List<T> collectionTask0(@NonNull List<T> firstList, @NonNull List<T> secondList) {
        List<T> mergedList = new ArrayList<>(firstList);
        mergedList.addAll(secondList);
        mergedList.sort(Collections.reverseOrder());
        return mergedList;
    }

    /**
     * Дан список. После каждого элемента добавьте предшествующую ему часть списка.
     *
     * @param inputList с исходными данными
     * @return измененный список
     * @throws NullPointerException если один из параметров null
     */
    public List<T> collectionTask1(@NonNull List<T> inputList) {
        List<T> resultList = new ArrayList<>();
        for (int i = 0; i < inputList.size(); i++) {
            T element = inputList.get(i);
            resultList.add(element);
            resultList.addAll(inputList.subList(0, i));
        }
        return resultList;
    }

    /**
     * Даны два списка. Определите, совпадают ли множества их элементов.
     *
     * @param firstList  первый список элементов
     * @param secondList второй список элементов
     * @return <tt>true</tt> если множества списков совпадают
     * @throws NullPointerException если один из параметров null
     */
    public boolean collectionTask2(@NonNull List<T> firstList, @NonNull List<T> secondList) {
        HashSet<T> set1 = new HashSet<>(firstList);
        HashSet<T> set2 = new HashSet<>(secondList);

        return set1.equals(set2);
    }

    /**
     * Создать список из заданного количества элементов.
     * Выполнить циклический сдвиг этого списка на N элементов вправо или влево.
     * Если N > 0 циклический сдвиг вправо.
     * Если N < 0 циклический сдвиг влево.
     *
     * @param inputList список, для которого выполняется циклический сдвиг влево
     * @param n         количество шагов циклического сдвига N
     * @return список inputList после циклического сдвига
     * @throws NullPointerException если один из параметров null
     */
    public List<T> collectionTask3(@NonNull List<T> inputList, int n) {
        List<T> resultList = new ArrayList<>(inputList);
        Collections.rotate(resultList, n);
        return resultList;
    }

    /**
     * Элементы списка хранят слова предложения.
     * Замените каждое вхождение слова A на B.
     *
     * @param inputList список со словами предложения и пробелами для разделения слов
     * @param a         слово, которое нужно заменить
     * @param b         слово, на которое нужно заменить
     * @return список после замены каждого вхождения слова A на слово В
     * @throws NullPointerException если один из параметров null
     */
    public List<String> collectionTask4(@NonNull List<String> inputList, @NonNull String a,
                                        @NonNull String b) {
        if (b == null) {
            throw new NullPointerException("Lists cannot be null");
        }
        List<String> resultList = new ArrayList<>();
        for (String word : inputList) {
            if (word.equals(a)) {
                resultList.add(b);
            } else {
                resultList.add(word);
            }
        }
        return resultList;
    }


    /*
      Задание подразумевает создание класса(ов) для выполнения задачи.

      Дан список студентов. Элемент списка содержит фамилию, имя, отчество, год рождения,
      курс, номер группы, оценки по пяти предметам. Заполните список и выполните задание.
      Упорядочите студентов по курсу, причем студенты одного курса располагались
      в алфавитном порядке. Найдите средний балл каждой группы по каждому предмету.
      Определите самого старшего студента и самого младшего студентов.
      Для каждой группы найдите лучшего с точки зрения успеваемости студента.
     */

    public class Student implements Comparable<Student> {
        private String lastName;
        private String firstName;
        private String middleName;
        private int birthYear;
        private int course;
        private int groupNumber;
        private List<Integer> grades;

        public Student(String lastName, String firstName, String middleName, int birthYear, int course, int groupNumber, List<Integer> grades) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.middleName = middleName;
            this.birthYear = birthYear;
            this.course = course;
            this.groupNumber = groupNumber;
            this.grades = grades;
        }

        // Геттеры и сеттеры для всех атрибутов

        @Override
        public int compareTo(Student other) {
            // Сравниваем студентов по курсу и алфавиту
            if (this.course != other.course) {
                return Integer.compare(this.course, other.course);
            } else {
                return this.lastName.compareTo(other.lastName);
            }
        }

        public int getGroupNumber() {
            return groupNumber;
        }

        public List<Integer> getGrades() {
            return grades;
        }

        public int getBirthYear() {
            return birthYear;
        }
    }

    public class StudentManager {
        private List<Student> students;

        public StudentManager() {
            students = new ArrayList<>();
        }

        public void addStudent(Student student) {
            students.add(student);
        }

        public void sortStudentsByCourse() {
            Collections.sort(students);
        }

        public double calculateAverageGradeByGroupAndSubject(int groupNumber, int subjectIndex) {
            List<Integer> grades = new ArrayList<>();
            for (Student student : students) {
                if (student.getGroupNumber() == groupNumber) {
                    grades.add(student.getGrades().get(subjectIndex));
                }
            }
            double sum = 0;
            for (int grade : grades) {
                sum += grade;
            }
            return sum / grades.size();
        }

        public Student findOldestStudent() {
            Student oldestStudent = students.get(0);
            for (Student student : students) {
                if (student.getBirthYear() < oldestStudent.getBirthYear()) {
                    oldestStudent = student;
                }
            }
            return oldestStudent;
        }

        public Student findYoungestStudent() {
            Student youngestStudent = students.get(0);
            for (Student student : students) {
                if (student.getBirthYear() > youngestStudent.getBirthYear()) {
                    youngestStudent = student;
                }
            }
            return youngestStudent;
        }

        public Student findBestStudentByGroup(int groupNumber) {
            List<Student> groupStudents = new ArrayList<>();
            for (Student student : students) {
                if (student.getGroupNumber() == groupNumber) {
                    groupStudents.add(student);
                }
            }
            Student bestStudent = groupStudents.get(0);
            for (Student student : groupStudents) {
                if (calculateAverageGrade(student) > calculateAverageGrade(bestStudent)) {
                    bestStudent = student;
                }
            }
            return bestStudent;
        }

        private double calculateAverageGrade(Student student) {
            double sum = 0;
            for (int grade : student.getGrades()) {
                sum += grade;
            }
            return sum / student.getGrades().size();
        }
    }

}