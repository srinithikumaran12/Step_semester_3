package accessmodifiers.assigment_problems;

/**
 * PROBLEM 4: LibraryMember JavaBean & Security Answer Property
 *
 * Note: named LibraryMemberProfile here (instead of LibraryMember) to avoid
 * a name clash with the LibraryMember class already defined for Problem 1
 * in this same package/branch.
 *
 * A fully JavaBean-compliant class with two special property rules:
 * - membershipId: write-once. A public setter exists (required by the
 *   framework's scan), but only the first call takes effect.
 * - securityAnswer: write-only. Stored as a one-way transformed value,
 *   with no getter anywhere on the class.
 */
public class LibraryMemberProfile {

    private String membershipId;
    private boolean membershipIdSet = false;

    private String name;
    private boolean premiumMember;

    @SuppressWarnings("unused")
    private int securityAnswerHash;

    public LibraryMemberProfile() {
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String id) {
        if (!membershipIdSet) {
            this.membershipId = id;
            this.membershipIdSet = true;
        }
        // every later call is silently ignored
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPremiumMember() {
        return premiumMember;
    }

    public void setPremiumMember(boolean premium) {
        this.premiumMember = premium;
    }

    // Write-only: stores a one-way transformed value, no getter exists anywhere
    public void setSecurityAnswer(String answer) {
        this.securityAnswerHash = (answer == null) ? 0 : answer.hashCode();
    }

    public static void main(String[] args) {
        LibraryMemberProfile m = new LibraryMemberProfile();
        m.setMembershipId("LIB-8841");
        m.setName("Priya Nair");
        m.setPremiumMember(true);
        System.out.println(m.getMembershipId()); // LIB-8841

        m.setMembershipId("FAKE-0000"); // silently ignored - write-once
        System.out.println(m.getMembershipId()); // LIB-8841

        System.out.println(m.isPremiumMember()); // true

        m.setSecurityAnswer("BlueMountain"); // no observable output, never retrievable
    }
}