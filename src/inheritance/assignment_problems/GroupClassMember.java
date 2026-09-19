package inheritance.assigment_problems;

/**
 * GroupClassMember extends GymMember directly, completely independent of
 * PremiumMember - hierarchical inheritance (a sibling branch, not a
 * descendant of PremiumMember).
 */
public class GroupClassMember extends GymMember {

    private final String className;

    // PROBLEM 2 constructor
    public GroupClassMember(String memberId, int monthlyFee, String className) {
        super(memberId, monthlyFee);
        this.className = className;
    }

    // PROBLEM 5 constructor - auto-generated memberId, chains to the GymMember(int) constructor's pattern
    public GroupClassMember(int monthlyFee, String className) {
        super(monthlyFee);
        this.className = className;
    }

    String getClassName() {
        return className;
    }

    @Override
    String displayInfo() {
        return "Group Class Member | Class: " + className + " | Sessions: " + getSessionsAttended();
    }
}