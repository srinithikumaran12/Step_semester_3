package inheritance.class_problems;

import java.util.Arrays;

/**
 * LibraryMember - base class shared across Problems 1 through 5.
 *
 * PROBLEM 1: validated constructor + enrollBatch()
 * PROBLEM 2: displayInfo() (overridden by subclasses), classifyGeneration(),
 *            getTotalBooksBorrowed()
 * PROBLEM 3: chargeFine() (overridden in StudentMember), private fine
 *            history with defensive copying
 * PROBLEM 4: batchPrint()
 * PROBLEM 5: final memberNumber from a shared static counter,
 *            isValidRenewalCode(), overloaded borrowBook(), processNightlyAudit()
 */
public class LibraryMember {

    private static int memberNumberCounter = 100;
    private static int membersEnrolled = 0;

    private final String memberNumber;
    private final String memberId;
    private final int borrowLimit;

    private int booksBorrowed;
    private String lastGenre;

    private final int[] fineHistory = new int[10];
    private int fineCount = 0;
    private int totalFine = 0;

    // PROBLEM 1: validated constructor taking an explicit memberId
    public LibraryMember(String memberId, int borrowLimit) {
        if (memberId == null || memberId.trim().isEmpty() || memberId.trim().length() < 4) {
            throw new IllegalArgumentException("construction rejected");
        }
        this.memberId = memberId;
        this.borrowLimit = borrowLimit;

        memberNumberCounter++;
        membersEnrolled++;
        this.memberNumber = "LIB-" + memberNumberCounter;
    }

    // PROBLEM 5: convenience constructor - auto-generates a valid memberId
    // and reuses the constructor above rather than duplicating its logic.
    public LibraryMember(int borrowLimit) {
        this("AUTO-GEN", borrowLimit);
    }

    // ---------- PROBLEM 1 ----------

    void borrowBook() {
        if (booksBorrowed < borrowLimit) {
            booksBorrowed++;
        }
    }

    int getBooksBorrowed() {
        return booksBorrowed;
    }

    static String enrollBatch(String[] memberIds, int borrowLimit) {
        int enrolled = 0;
        int rejected = 0;

        for (String id : memberIds) {
            try {
                new LibraryMember(id, borrowLimit); // validation lives only in the constructor
                enrolled++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Enrolled: " + enrolled + " | Rejected: " + rejected;
    }

    // ---------- PROBLEM 2 ----------

    String displayInfo() {
        return "General Member | Books Borrowed: " + booksBorrowed;
    }

    static String classifyGeneration(LibraryMember member) {
        if (member instanceof HonorsStudentMember) {
            return "Multilevel descendant (3 generations deep)";
        } else if (member instanceof FacultyMember) {
            return "Hierarchical sibling (independent branch)";
        } else if (member instanceof StudentMember) {
            return "Direct descendant (1 generation deep)";
        }
        return "Base member (no descendant)";
    }

    static int getTotalBooksBorrowed(LibraryMember[] members) {
        int total = 0;
        for (LibraryMember member : members) {
            total += member.getBooksBorrowed(); // polymorphic call
        }
        return total;
    }

    // ---------- PROBLEM 3 ----------

    protected void chargeFine(int amount) {
        if (fineCount < fineHistory.length) {
            fineHistory[fineCount++] = amount;
        }
        totalFine += amount;
    }

    int[] getFineHistory() {
        return Arrays.copyOf(fineHistory, fineCount); // defensive copy out
    }

    int getTotalFine() {
        return totalFine;
    }

    // ---------- PROBLEM 4 ----------

    static String batchPrint(LibraryMember[] members) {
        StringBuilder report = new StringBuilder();

        for (LibraryMember member : members) {
            report.append(member.displayInfo()); // polymorphic - no instanceof chain here

            if (member instanceof StudentMember) {
                StudentMember student = (StudentMember) member; // safe: guarded by instanceof above
                report.append(" [Course via downcast: ").append(student.getCourse()).append("]");
            }

            report.append(" | ");
        }

        return report.toString();
    }

    // ---------- PROBLEM 5 ----------

    static boolean isValidRenewalCode(String code) {
        if (code == null || code.length() != 4) {
            return false;
        }
        return code.charAt(0) == 'R'
                && Character.isDigit(code.charAt(1))
                && Character.isDigit(code.charAt(2))
                && Character.isUpperCase(code.charAt(3));
    }

    static int getMembersEnrolled() {
        return membersEnrolled;
    }

    void borrowBook(String genre) {
        this.lastGenre = genre; // record genre first
        borrowBook();           // then delegate to the no-argument version
    }

    static String processNightlyAudit(LibraryMember[] members) {
        int processed = 0;
        int nullSkipped = 0;
        int faculty = 0;
        int regular = 0;

        for (LibraryMember member : members) {
            if (member == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (member instanceof FacultyMember) {
                faculty++;
            } else {
                regular++;
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | "
                + faculty + " faculty | " + regular + " regular";
    }

    // ---------- shared getters ----------

    String getMemberId() {
        return memberId;
    }

    String getMemberNumber() {
        return memberNumber;
    }
}