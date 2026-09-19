package string.assigment_problems;

/**
 * Assignment 1: The Exam Hall Seat Duplication Checker
 * Scenario: The Examination Cell manages seat allocation for hundreds of
 * students and must confirm no seat number has been assigned twice before
 * an exam begins.
 */
public class ExamHallSeatDuplicationChecker {

    void checkDuplicateSeats(int[] seatNumbers) {
        boolean[] alreadyReported = new boolean[seatNumbers.length];
        boolean foundAny = false;

        for (int i = 0; i < seatNumbers.length; i++) {
            if (alreadyReported[i]) {
                continue;
            }
            for (int j = i + 1; j < seatNumbers.length; j++) {
                if (seatNumbers[i] == seatNumbers[j]) {
                    System.out.println("Duplicate Seat Number Found: " + seatNumbers[i]);
                    alreadyReported[j] = true;
                    foundAny = true;
                }
            }
        }

        if (!foundAny) {
            System.out.println("No Duplicate Seats Found");
        }
    }

    public static void main(String[] args) {
        ExamHallSeatDuplicationChecker checker = new ExamHallSeatDuplicationChecker();

        checker.checkDuplicateSeats(new int[]{101, 102, 103, 102, 105});
        // Duplicate Seat Number Found: 102

        checker.checkDuplicateSeats(new int[]{101, 102, 103, 104, 105});
        // No Duplicate Seats Found
    }
}