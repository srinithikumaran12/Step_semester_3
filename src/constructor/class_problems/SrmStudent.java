package constructor.class_problems;

/**
 * M4: One-Time College Setup, Many Students
 * Scenario: SRM's student portal wants the college name and academic year
 * loaded exactly once - via a static block, regardless of how many
 * students are created afterward.
 */
public class SrmStudent {

    static String collegeName;
    static String academicYear;

    String name;

    static {
        collegeName = "SRM Institute of Science and Technology";
        academicYear = "2025-2026";
        System.out.println("College info loaded");
    }

    public SrmStudent(String name) {
        this.name = name;
        System.out.println("Student record created: " + name);
    }

    public static void main(String[] args) {
        String[] names = {"Ravi", "Meera", "Karthik", "Divya", "Anitha"};

        for (String name : names) {
            new SrmStudent(name);
        }
        // College info loaded
        // Student record created: Ravi
        // Student record created: Meera
        // Student record created: Karthik
        // Student record created: Divya
        // Student record created: Anitha
    }
}