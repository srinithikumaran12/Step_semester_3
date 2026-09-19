package inheritance.assigment_problems;

/**
 * PROBLEM 4: The Monthly Attendance Announcer
 */
public class Problem4AttendanceAnnouncerDemo {

    public static void main(String[] args) {
        GymMember[] members = {
                new GymMember("MEM6", 1000),
                new PremiumMember("MEM7", 2000, "Coach Riya")
        };

        System.out.println(GymMember.batchPrint(members));
        // Standard Member | Sessions: 0 | Premium Member | Trainer: Coach Riya | Sessions: 0 [Trainer via downcast: Coach Riya] |

        GymMember plain = new GymMember("MEM8", 1000);
        try {
            PremiumMember bad = (PremiumMember) plain; // compiles fine, fails at runtime
        } catch (ClassCastException e) {
            System.out.println("ClassCastException at runtime");
        }
    }
}