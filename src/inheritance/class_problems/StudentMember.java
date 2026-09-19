package inheritance.class_problems;

/**
 * StudentMember extends LibraryMember directly (single inheritance).
 * Forwards shared fields via super(...) - does not duplicate memberId or
 * borrowLimit as its own fields.
 */
public class StudentMember extends LibraryMember {

    private final String course;

    public StudentMember(String memberId, int borrowLimit, String course) {
        super(memberId, borrowLimit);
        this.course = course;
    }

    String getCourse() {
        return course;
    }

    @Override
    String displayInfo() {
        return "Student Member | Course: " + course + " | Books Borrowed: " + getBooksBorrowed();
    }

    // PROBLEM 3: halves every fine before applying it via the parent's own logic
    @Override
    protected void chargeFine(int amount) {
        super.chargeFine(amount / 2);
    }
}