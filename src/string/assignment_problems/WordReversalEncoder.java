package string.assigment_problems;

/**
 * Assignment 2: Word Reversal Encoder
 * Scenario: The coding club's "mirror text" mini-game reverses every word
 * in a sentence individually while keeping the word order the same.
 */
public class WordReversalEncoder {

    String reverseEachWord(String sentence) {
        String[] words = sentence.split(" ");
        StringBuilder result = new StringBuilder();

        for (int w = 0; w < words.length; w++) {
            String word = words[w];
            StringBuilder reversedWord = new StringBuilder();

            for (int i = word.length() - 1; i >= 0; i--) {
                reversedWord.append(word.charAt(i));
            }

            result.append(reversedWord);
            if (w < words.length - 1) {
                result.append(" ");
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {
        WordReversalEncoder encoder = new WordReversalEncoder();
        System.out.println(encoder.reverseEachWord("hello club")); // olleh bulc
    }
}