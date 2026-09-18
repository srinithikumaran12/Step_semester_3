package string.class_problems;

/**
 * Problem 5: Bank Transaction Reference Generator & Validator
 * Scenario: A fintech onboarding module for a placement-prep hackathon
 * needs to both normalize and validate transaction reference codes.
 * A valid reference is exactly 14 characters: 3 letters (bank code)
 * + 6 digits (date, ddMMyy) + 5 digits (sequence number).
 */
public class BankTransactionReference {

    String normalizeReference(String raw) {
        String trimmed = raw.trim();

        if (trimmed.length() < 3) {
            return trimmed; // too short, let validation catch it
        }

        String bankCode = trimmed.substring(0, 3).toUpperCase();
        String rest = trimmed.substring(3);

        return bankCode + rest;
    }

    String validateAndFormat(String reference) {
        if (reference.length() != 14) {
            return "Invalid: reference must be exactly 14 characters";
        }

        String bankCode = reference.substring(0, 3);
        String datePart = reference.substring(3, 9);
        String seqPart = reference.substring(9, 14);

        for (int i = 0; i < bankCode.length(); i++) {
            if (!Character.isLetter(bankCode.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }

        String body = datePart + seqPart;
        for (int i = 0; i < body.length(); i++) {
            if (!Character.isDigit(body.charAt(i))) {
                return "Invalid: date and sequence must be numeric";
            }
        }

        String dd = datePart.substring(0, 2);
        String mm = datePart.substring(2, 4);
        String yy = datePart.substring(4, 6);

        StringBuilder result = new StringBuilder();
        result.append("[").append(bankCode).append("] ")
              .append("DATE: ").append(dd).append("/").append(mm).append("/").append(yy)
              .append(" | SEQ: ").append(seqPart);

        return result.toString();
    }

    public static void main(String[] args) {
        BankTransactionReference ref = new BankTransactionReference();

        String normalized1 = ref.normalizeReference(" hdf03022600042 ");
        System.out.println(ref.validateAndFormat(normalized1));
        // [HDF] DATE: 03/02/26 | SEQ: 00042

        String normalized2 = ref.normalizeReference("12F03022600042");
        System.out.println(ref.validateAndFormat(normalized2));
        // Invalid: bank code must be 3 letters
    }
}