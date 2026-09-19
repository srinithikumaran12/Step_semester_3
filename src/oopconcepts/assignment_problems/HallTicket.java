package oopconcepts.assigment_problems;

/**
 * M4: Reference Copies and a Shared Exam Hall Ticket
 * Scenario: Two "different" variables in a script both claim to represent
 * Priya's exam hall ticket, but only one of them is actually real.
 */
public class HallTicket {

    String studentName;
    int seatNumber;

    public HallTicket(String studentName, int seatNumber) {
        this.studentName = studentName;
        this.seatNumber = seatNumber;
    }

    public static void main(String[] args) {
        HallTicket priya = new HallTicket("Priya", 0);
        HallTicket copy = priya; // same object, not a new one

        copy.seatNumber = 45;

        System.out.println("Priya's seatNumber (via first variable): " + priya.seatNumber);
        // Priya's seatNumber (via first variable): 45

        System.out.println("copy == priya: " + (copy == priya));
        // copy == priya: true

        HallTicket separate = new HallTicket("Priya", 45); // identical values, different object
        System.out.println("separate == priya: " + (separate == priya));
        // separate == priya: false
    }
}