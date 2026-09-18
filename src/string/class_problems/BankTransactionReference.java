import java.util.Scanner;

public class BankTransactionReference {

    static String normalizeReference(String raw) {

        // Remove leading and trailing spaces
        String reference = raw.trim();

        // Convert first 3 characters to uppercase
        if (reference.length() >= 3) {
            String bankCode = reference.substring(0, 3).toUpperCase();
            String remaining = reference.substring(3);

            reference = bankCode + remaining;
        }

        return reference;
    }

    static String validateAndFormat(String reference) {

        // Check length
        if (reference.length() != 14) {
            return "Invalid: wrong length";
        }

        // Check first 3 characters are letters
        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(reference.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }

        // Check remaining 11 characters are digits
        for (int i = 3; i < 14; i++) {
            if (!Character.isDigit(reference.charAt(i))) {
                return "Invalid: body must contain digits";
            }
        }

        // Extract parts
        String bankCode = reference.substring(0, 3);
        String date = reference.substring(3, 9);
        String sequence = reference.substring(9, 14);

        // Format date: ddMMyy -> dd/MM/yy
        String formattedDate =
                date.substring(0, 2) + "/" +
                date.substring(2, 4) + "/" +
                date.substring(4, 6);

        // Build final output
        StringBuilder result = new StringBuilder();

        result.append("[");
        result.append(bankCode);
        result.append("] DATE: ");
        result.append(formattedDate);
        result.append(" | SEQ: ");
        result.append(sequence);

        return result.toString();
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter transaction reference: ");
        String raw = sc.nextLine();

        // Step 1: Normalize
        String normalized = normalizeReference(raw);

        // Step 2: Validate and format
        String result = validateAndFormat(normalized);

        System.out.println(result);

        sc.close();
    }
}