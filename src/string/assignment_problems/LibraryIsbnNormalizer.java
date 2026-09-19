package string.assigment_problems;

/**
 * Assignment 4: Library ISBN Normalizer & Validator
 * Scenario: A library system's book-intake scanner needs to normalize and
 * validate ISBN-style codes: 3 letters (publisher code) + 4 digits (year)
 * + 6 digits (catalog number) = 13 characters total.
 */
public class LibraryIsbnNormalizer {

    String normalizeCode(String raw) {
        String trimmed = raw.trim();

        if (trimmed.length() < 3) {
            return trimmed; // too short, let validation catch it
        }

        String publisherCode = trimmed.substring(0, 3).toUpperCase();
        String rest = trimmed.substring(3);

        return publisherCode + rest;
    }

    String validateAndFormat(String code) {
        if (code.length() != 13) {
            return "Invalid: code must be exactly 13 characters";
        }

        String publisherCode = code.substring(0, 3);
        String year = code.substring(3, 7);
        String catalogNumber = code.substring(7, 13);

        for (int i = 0; i < publisherCode.length(); i++) {
            if (!Character.isLetter(publisherCode.charAt(i))) {
                return "Invalid: publisher code must be 3 letters";
            }
        }

        String body = year + catalogNumber;
        for (int i = 0; i < body.length(); i++) {
            if (!Character.isDigit(body.charAt(i))) {
                return "Invalid: year and catalog number must be numeric";
            }
        }

        StringBuilder result = new StringBuilder();
        result.append("[").append(publisherCode).append("] ")
              .append("YEAR: ").append(year)
              .append(" | CATALOG: ").append(catalogNumber);

        return result.toString();
    }

    public static void main(String[] args) {
        LibraryIsbnNormalizer normalizer = new LibraryIsbnNormalizer();

        String normalized1 = normalizer.normalizeCode(" pen2026004251 ");
        System.out.println(normalizer.validateAndFormat(normalized1));
        // [PEN] YEAR: 2026 | CATALOG: 004251

        String normalized2 = normalizer.normalizeCode("12N2026004251");
        System.out.println(normalizer.validateAndFormat(normalized2));
        // Invalid: publisher code must be 3 letters
    }
}