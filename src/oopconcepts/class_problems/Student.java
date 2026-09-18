package oopconcepts.class_problems;

/**
 * M5: Instance vs Static - Splitting a Class Correctly
 * Scenario: A club member's first draft of Student stores attendance, name,
 * AND the college name as instance fields - meaning every single student
 * object ends up with its own copy of "SRM Institute of Science and
 * Technology" typed in separately. Fix the design.
 */
public class Student {

    String name;
    int attendance;

    static String collegeName = "SRM Institute of Science and Technology";
    static int studentCount = 0;

    public Student(String name, int attendance) {
        this.name = name;
        this.attendance = attendance;
        studentCount++;
    }

    static void printCollegeInfo() {
        System.out.println(collegeName);
        System.out.println("Students created: " + studentCount);
    }

    public static void main(String[] args) {
        Student s1 = new Student("Ravi", 90);
        Student s2 = new Student("Anitha", 95);

        Student.printCollegeInfo();
        // SRM Institute of Science and Technology
        // Students created: 2
    }
}