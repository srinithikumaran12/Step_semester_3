package oopconcepts.class_problems;

/**
 * M4: Reference Copies and a Shared ID Card
 * Scenario: Two "different" variables in a script both claim to represent
 * Ravi's library card, but only one of them is actually real.
 * Prove it with code.
 */
public class IdCard {

    String name;
    int booksIssued;

    public IdCard(String name, int booksIssued) {
        this.name = name;
        this.booksIssued = booksIssued;
    }

    public static void main(String[] args) {
        IdCard ravi = new IdCard("Ravi", 0);
        IdCard duplicate = ravi; // same object, not a copy

        duplicate.booksIssued = 3;

        System.out.println("Ravi's booksIssued (via first variable): " + ravi.booksIssued);
        // Ravi's booksIssued (via first variable): 3

        System.out.println("duplicate == ravi: " + (duplicate == ravi));
        // duplicate == ravi: true

        IdCard separate = new IdCard("Ravi", 3); // identical values, different object
        System.out.println("separate == ravi: " + (separate == ravi));
        // separate == ravi: false
    }
}