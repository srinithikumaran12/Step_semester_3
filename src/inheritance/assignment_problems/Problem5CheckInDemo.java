package inheritance.assigment_problems;

/**
 * PROBLEM 5: Membership Numbers, Referral Codes & the Weekly Check-in Settlement
 */
public class Problem5CheckInDemo {

    public static void main(String[] args) {
        GymMember m1 = new GymMember(1000);
        System.out.println(m1.getMembershipNumber());            // GYM-2001 (first auto-numbered member in this run)
        System.out.println(GymMember.getMembersEnrolled());       // reflects total constructed so far

        System.out.println(GymMember.isValidReferralCode("G45B")); // true
        System.out.println(GymMember.isValidReferralCode("G4B"));  // false - too short
        System.out.println(GymMember.isValidReferralCode("X45B")); // false - wrong leading letter

        m1.payFee(500);
        m1.payFee(500, "UPI"); // records mode, then delegates to the flat-amount version
        System.out.println(m1.getFeesPaid()); // 1000

        GymMember[] members = {
                new GroupClassMember(1500, "Zumba"),
                null,
                new GymMember(1000)
        };
        System.out.println(GymMember.processWeeklyCheckIn(members));
        // 2 processed | 1 null skipped | 1 group | 1 individual
    }
}