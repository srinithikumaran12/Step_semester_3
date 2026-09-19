package string.assigment_problems;

/**
 * Assignment 2: The Typing Speed Test Accuracy Checker
 * Scenario: Compare a user's typed text against the original passage,
 * character by character, and report accuracy plus the first mismatch.
 */
public class TypingSpeedTestAccuracyChecker {

    void checkTypingAccuracy(String original, String typed) {
        int matched = 0;
        int firstMismatchIndex = -1;

        for (int i = 0; i < original.length(); i++) {
            if (original.charAt(i) == typed.charAt(i)) {
                matched++;
            } else if (firstMismatchIndex == -1) {
                firstMismatchIndex = i;
            }
        }

        double accuracy = (matched / (double) original.length()) * 100;

        String result = String.format("Matched: %d/%d | Accuracy: %.2f%%", matched, original.length(), accuracy);

        if (firstMismatchIndex == -1) {
            result += " | No Mismatches";
        } else {
            result += " | First Mismatch at position " + (firstMismatchIndex + 1)
                    + " ('" + original.charAt(firstMismatchIndex) + "' vs '" + typed.charAt(firstMismatchIndex) + "')";
        }

        System.out.println(result);
    }

    public static void main(String[] args) {
        TypingSpeedTestAccuracyChecker checker = new TypingSpeedTestAccuracyChecker();

        checker.checkTypingAccuracy("hello world", "hello worlt");
        // Matched: 10/11 | Accuracy: 90.91% | First Mismatch at position 11 ('d' vs 't')

        checker.checkTypingAccuracy("coding", "coding");
        // Matched: 6/6 | Accuracy: 100.00% | No Mismatches
    }
}