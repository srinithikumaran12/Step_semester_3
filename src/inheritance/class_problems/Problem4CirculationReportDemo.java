package inheritance.class_problems;

/**
 * PROBLEM 4: The Weekly Circulation Report
 */
public class Problem4CirculationReportDemo {

    public static void main(String[] args) {
        LibraryMember[] members = {
                new LibraryMember("LB5", 3),
                new StudentMember("STU6", 3, "ECE")
        };

        System.out.println(LibraryMember.batchPrint(members));
        // General Member | Books Borrowed: 0 | Student Member | Course: ECE | Books Borrowed: 0 [Course via downcast: ECE] |

        LibraryMember plain = new LibraryMember("LB6", 3);
        try {
            StudentMember bad = (StudentMember) plain; // compiles fine, fails at runtime
        } catch (ClassCastException e) {
            System.out.println("ClassCastException at runtime");
        }
    }
}