package accessmodifiers.assigment_problems;

/**
 * PROBLEM 1 (continued): LibraryMember with the right access level per field.
 *
 * - membershipPin: private   -> must be inaccessible outside this class entirely
 * - branchCode:    default   -> reachable only by classes in the same package
 * - finesOwed:     protected -> same-package reachable now; cross-package
 *                               subclass behavior is the focus of Problem 2
 * - displayName:   public    -> reachable from anywhere
 */
public class LibraryMember {

    private String membershipPin;
    String branchCode;
    protected double finesOwed;
    public String displayName;

    public LibraryMember(String membershipPin, String branchCode, double finesOwed, String displayName) {
        this.membershipPin = membershipPin;
        this.branchCode = branchCode;
        this.finesOwed = finesOwed;
        this.displayName = displayName;
    }

    public String getMembershipPin() {
        return membershipPin;
    }
}