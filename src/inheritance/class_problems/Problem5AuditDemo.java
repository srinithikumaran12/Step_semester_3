package inheritance.class_problems;

/**
 * PROBLEM 5: Membership Numbers, Renewal Codes & the Nightly Circulation Audit
 */
public class Problem5AuditDemo {

    public static void main(String[] args) {
        LibraryMember m1 = new LibraryMember(3);
        System.out.println(m1.getMemberNumber());               // LIB-101 (first auto-numbered member in this run)
        System.out.println(LibraryMember.getMembersEnrolled());  // reflects total constructed so far

        System.out.println(LibraryMember.isValidRenewalCode("R12A")); // true
        System.out.println(LibraryMember.isValidRenewalCode("R1A"));  // false - too short
        System.out.println(LibraryMember.isValidRenewalCode("X12A")); // false - wrong leading letter

        m1.borrowBook();
        m1.borrowBook("Fiction"); // records genre, then delegates to no-arg borrowBook()
        System.out.println(m1.getBooksBorrowed()); // 2

        LibraryMember[] members = {
                new FacultyMember(5, "Physics"),
                null,
                new LibraryMember(3)
        };
        System.out.println(LibraryMember.processNightlyAudit(members));
        // 2 processed | 1 null skipped | 1 faculty | 1 regular
    }
}