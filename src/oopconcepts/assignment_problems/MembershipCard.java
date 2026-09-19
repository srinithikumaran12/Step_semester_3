package constructor.assigment_problems;

/**
 * A4: Static Block - Library Membership Card Setup
 * Scenario: The library issues membership cards to a fresh batch of
 * students, wanting the library's name and validity year set exactly once.
 */
public class MembershipCard {

    static String libraryName;
    static String validUntil;

    String studentName;

    static {
        libraryName = "SRM Central Library";
        validUntil = "May 2027";
        System.out.println("Library info loaded");
    }

    public MembershipCard(String studentName) {
        this.studentName = studentName;
        System.out.println("Membership card issued: " + studentName);
    }

    public static void main(String[] args) {
        String[] names = {"Ananya", "Rohan", "Priya", "Arjun", "Sneha"};

        for (String name : names) {
            new MembershipCard(name);
        }
        // Library info loaded
        // Membership card issued: Ananya
        // Membership card issued: Rohan
        // Membership card issued: Priya
        // Membership card issued: Arjun
        // Membership card issued: Sneha
    }
}