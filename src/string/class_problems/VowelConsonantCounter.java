package string.class_problems;

/**
 * Problem 1: Vowel & Consonant Counter
 * Scenario: A library orientation kiosk counts vowels and consonants
 * in a submitted book title for a simple text-stats display.
 */
public class VowelConsonantCounter {

    void countVowelsAndConsonants(String text) {
        int vowels = 0;
        int consonants = 0;

        for (int i = 0; i < text.length(); i++) {
            char ch = Character.toLowerCase(text.charAt(i));

            if (ch == ' ') {
                continue; // ignore spaces
            }

            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                vowels++;
            } else if (ch >= 'a' && ch <= 'z') {
                consonants++;
            }
        }

        System.out.println("Vowels: " + vowels + " | Consonants: " + consonants);
    }

    public static void main(String[] args) {
        VowelConsonantCounter counter = new VowelConsonantCounter();
        counter.countVowelsAndConsonants("Java Programming"); // Vowels: 5 | Consonants: 10
    }
}