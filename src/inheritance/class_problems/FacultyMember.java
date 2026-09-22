package inheritance.class_problems;

/**
 * FacultyMember extends LibraryMember directly, completely independent of
 * StudentMember - hierarchical inheritance (a sibling branch, not a
 * descendant of StudentMember).
 */
public class FacultyMember extends LibraryMember {

    private final String department;

    // PROBLEM 2 constructor
    public FacultyMember(String memberId, int borrowLimit, String department) {
        super(memberId, borrowLimit);
        this.department = department;
    }

    // PROBLEM 5 constructor - auto-generated memberId, chains to the LibraryMember(int) constructor's pattern
    public FacultyMember(int borrowLimit, String department) {
        super(borrowLimit);
        this.department = department;
    }

    String getDepartment() {
        return department;
    }

    @Override
    String displayInfo() {
        return "Faculty Member | Department: " + department + " | Books Borrowed: " + getBooksBorrowed();
    }
}