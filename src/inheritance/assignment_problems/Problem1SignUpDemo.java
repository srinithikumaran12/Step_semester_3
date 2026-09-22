package inheritance.assigment_problems;

/**
 * PROBLEM 1: Gym Membership Foundation & Batch Trial Sign-up Validator
 */
public class Problem1SignUpDemo {

    public static void main(String[] args) {
        try {
            new GymMember("GM1", 1000); // "GM1" is only 3 characters
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // construction rejected
        }

        PremiumMember p = new PremiumMember("MEM01", 2000, "Coach Riya");
        p.attendSession();
        p.attendSession();
        System.out.println(p.getSessionsAttended()); // 2

        String[] memberIds = {"MEM1", "GM1", "MEM2", " ", "MEM3"};
        System.out.println(GymMember.signUpBatch(memberIds, 1000)); // Signed Up: 3 | Rejected: 2
    }
}