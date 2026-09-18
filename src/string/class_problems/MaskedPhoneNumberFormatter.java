package string.class_problems;

/**
 * Problem 4: Masked Phone Number Formatter
 * Scenario: A student-support call center displays a partially masked
 * version of a registered phone number on-screen for privacy, while
 * agents confirm identity using the last 4 digits.
 */
public class MaskedPhoneNumberFormatter {

    String maskPhoneNumber(String phone) {
        if (phone.length() != 10) {
            return "Invalid phone number";
        }

        for (int i = 0; i < phone.length(); i++) {
            if (!Character.isDigit(phone.charAt(i))) {
                return "Invalid phone number";
            }
        }

        String lastFour = phone.substring(phone.length() - 4);

        StringBuilder masked = new StringBuilder("XXXXXX");
        masked.append("-").append(lastFour);

        return masked.toString();
    }

    public static void main(String[] args) {
        MaskedPhoneNumberFormatter formatter = new MaskedPhoneNumberFormatter();
        System.out.println(formatter.maskPhoneNumber("9876543210")); // XXXXXX-3210
        System.out.println(formatter.maskPhoneNumber("98765")); // Invalid phone number
    }
}