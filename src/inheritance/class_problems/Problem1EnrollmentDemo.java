package inheritance.class_problems;

/**
 * PROBLEM 1: Library Membership Foundation & Batch Enrollment Validator
 */
public class Problem1EnrollmentDemo {

    public static void main(String[] args) {
        try {
            new LibraryMember("LB1", 3); // "LB1" is only 3 characters
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // construction rejected
        }

        StudentMember s = new StudentMember("STU10", 3, "CSE");
        s.borrowBook();
        s.borrowBook();
        System.out.println(s.getBooksBorrowed()); // 2

        String[] memberIds = {"STU1", "LB1", "STU2", " ", "STU3"};
        System.out.println(LibraryMember.enrollBatch(memberIds, 3)); // Enrolled: 3 | Rejected: 2
    }
}