package accessmodifiers.assigment_problems;

/**
 * PROBLEM 2: Reference Desk Subclass Reach
 * PremiumLibraryMember extends LibraryMember and reads the protected
 * finesOwed field through ITS OWN type (SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE,
 * allowed) - not through a plain LibraryMember-typed reference
 * (SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE, denied).
 */
public class PremiumLibraryMember extends LibraryMember {

    private double loyaltyDiscount;

    public PremiumLibraryMember(String membershipPin, String branchCode, double finesOwed,
                                 String displayName, double loyaltyDiscount) {
        super(membershipPin, branchCode, finesOwed, displayName);
        this.loyaltyDiscount = loyaltyDiscount;
    }

    double discountedFinesOwed() {
        // Accessing protected finesOwed through "this" (own type) - ALLOWED
        return this.finesOwed * (1 - loyaltyDiscount);
    }

    public static void main(String[] args) {
        PremiumLibraryMember member = new PremiumLibraryMember("PIN1234", "BR01", 100.0, "Priya Nair", 0.1);
        System.out.println("Discounted fines: " + member.discountedFinesOwed()); // 90.0
    }
}