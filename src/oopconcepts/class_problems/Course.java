package oopconcepts.class_problems;

/**
 * M3: Overloaded Constructors for a Course
 * Scenario: Some courses come with a separate lab component and lab credit
 * count; most don't. Support both without writing the same setup logic
 * twice.
 */
public class Course {

    String code;
    String title;
    int credits;
    int labCredits;

    public Course(String code, String title, int credits, int labCredits) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.labCredits = labCredits;
    }

    // Theory-only course: chains to the 4-argument constructor with labCredits = 0
    public Course(String code, String title, int credits) {
        this(code, title, credits, 0);
    }

    int totalCredits() {
        return credits + labCredits;
    }

    public static void main(String[] args) {
        Course theoryOnly = new Course("21CSC201J", "Data Structures", 4);
        Course withLab = new Course("21CSC205L", "DSA Lab", 3, 1);

        System.out.println(theoryOnly.code + " total credits: " + theoryOnly.totalCredits());
        // 21CSC201J total credits: 4

        System.out.println(withLab.code + " total credits: " + withLab.totalCredits());
        // 21CSC205L total credits: 4
    }
}