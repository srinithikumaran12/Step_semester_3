package string.assigment_problems;

/**
 * Assignment 1: ATM PIN Length Validator
 * Scenario: An ATM app must check that a PIN is exactly 4 digits long
 * before allowing the customer to continue.
 */
public class AtmPinLengthValidator {

    void checkPinLength(String pin) {
        if (pin.length() != 4) {
            System.out.println("Invalid PIN — must be exactly 4 digits.");
        } else {
            System.out.println("PIN length OK.");
        }
    }

    public static void main(String[] args) {
        AtmPinLengthValidator validator = new AtmPinLengthValidator();

        validator.checkPinLength("482");  // Invalid PIN — must be exactly 4 digits.
        validator.checkPinLength("4820"); // PIN length OK.
    }
}