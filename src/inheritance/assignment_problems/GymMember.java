package inheritance.assigment_problems;

import java.util.Arrays;

/**
 * GymMember - base class shared across Problems 1 through 5.
 *
 * PROBLEM 1: validated constructor + signUpBatch()
 * PROBLEM 2: displayInfo() (overridden by subclasses), classifyGeneration(),
 *            getTotalSessionsAttended()
 * PROBLEM 3: chargeLateFee() (overridden in PremiumMember), private late
 *            fee history with defensive copying
 * PROBLEM 4: batchPrint()
 * PROBLEM 5: final membershipNumber from a shared static counter,
 *            isValidReferralCode(), overloaded payFee(), processWeeklyCheckIn()
 */
public class GymMember {

    private static int membershipNumberCounter = 2000;
    private static int membersEnrolled = 0;

    private final String membershipNumber;
    private final String memberId;
    private final int monthlyFee;

    private int sessionsAttended;
    private String lastPaymentMode;
    private int feesPaid = 0;

    private final int[] lateFeeHistory = new int[10];
    private int lateFeeCount = 0;
    private int totalLateFees = 0;

    // PROBLEM 1: validated constructor taking an explicit memberId
    public GymMember(String memberId, int monthlyFee) {
        if (memberId == null || memberId.trim().isEmpty() || memberId.trim().length() < 4) {
            throw new IllegalArgumentException("construction rejected");
        }
        this.memberId = memberId;
        this.monthlyFee = monthlyFee;

        membershipNumberCounter++;
        membersEnrolled++;
        this.membershipNumber = "GYM-" + membershipNumberCounter;
    }

    // PROBLEM 5: convenience constructor - auto-generates a valid memberId
    // and reuses the constructor above rather than duplicating its logic.
    public GymMember(int monthlyFee) {
        this("AUTO-GEN", monthlyFee);
    }

    // ---------- PROBLEM 1 ----------

    void attendSession() {
        sessionsAttended++;
    }

    int getSessionsAttended() {
        return sessionsAttended;
    }

    static String signUpBatch(String[] memberIds, int monthlyFee) {
        int signedUp = 0;
        int rejected = 0;

        for (String id : memberIds) {
            try {
                new GymMember(id, monthlyFee); // validation lives only in the constructor
                signedUp++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        return "Signed Up: " + signedUp + " | Rejected: " + rejected;
    }

    // ---------- PROBLEM 2 ----------

    String displayInfo() {
        return "Standard Member | Sessions: " + sessionsAttended;
    }

    static String classifyGeneration(GymMember member) {
        if (member instanceof EliteMember) {
            return "Multilevel descendant (3 generations deep)";
        } else if (member instanceof GroupClassMember) {
            return "Hierarchical sibling (independent branch)";
        } else if (member instanceof PremiumMember) {
            return "Direct descendant (1 generation deep)";
        }
        return "Base member (no descendant)";
    }

    static int getTotalSessionsAttended(GymMember[] members) {
        int total = 0;
        for (GymMember member : members) {
            total += member.getSessionsAttended(); // polymorphic call
        }
        return total;
    }

    // ---------- PROBLEM 3 ----------

    protected void chargeLateFee(int amount) {
        if (lateFeeCount < lateFeeHistory.length) {
            lateFeeHistory[lateFeeCount++] = amount;
        }
        totalLateFees += amount;
    }

    int[] getLateFeeHistory() {
        return Arrays.copyOf(lateFeeHistory, lateFeeCount); // defensive copy out
    }

    int getTotalLateFees() {
        return totalLateFees;
    }

    // ---------- PROBLEM 4 ----------

    static String batchPrint(GymMember[] members) {
        StringBuilder announcement = new StringBuilder();

        for (GymMember member : members) {
            announcement.append(member.displayInfo()); // polymorphic - no instanceof chain here

            if (member instanceof PremiumMember) {
                PremiumMember premium = (PremiumMember) member; // safe: guarded by instanceof above
                announcement.append(" [Trainer via downcast: ").append(premium.getTrainerName()).append("]");
            }

            announcement.append(" | ");
        }

        return announcement.toString();
    }

    // ---------- PROBLEM 5 ----------

    static boolean isValidReferralCode(String code) {
        if (code == null || code.length() != 4) {
            return false;
        }
        return code.charAt(0) == 'G'
                && Character.isDigit(code.charAt(1))
                && Character.isDigit(code.charAt(2))
                && Character.isUpperCase(code.charAt(3));
    }

    static int getMembersEnrolled() {
        return membersEnrolled;
    }

    void payFee(int amount) {
        feesPaid += amount;
    }

    void payFee(int amount, String mode) {
        this.lastPaymentMode = mode; // record mode first
        payFee(amount);              // then delegate to the flat-amount version
    }

    int getFeesPaid() {
        return feesPaid;
    }

    static String processWeeklyCheckIn(GymMember[] members) {
        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (GymMember member : members) {
            if (member == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (member instanceof GroupClassMember) {
                group++;
            } else {
                individual++;
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | "
                + group + " group | " + individual + " individual";
    }

    // ---------- shared getters ----------

    String getMemberId() {
        return memberId;
    }

    String getMembershipNumber() {
        return membershipNumber;
    }
}