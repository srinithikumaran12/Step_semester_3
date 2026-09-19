package string.class_problems;

import java.util.Scanner;

public class PalindromeChecker {

    public static boolean isPalindromeIterative(String text) {
        int start = 0;
        int end = text.length() - 1;

        while (start < end) {
            if (text.charAt(start) != text.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }

        return true;
    }

    public static boolean isPalindromeRecursive(String text) {
        if (text.length() <= 1) {
            return true;
        }

        if (text.charAt(0) != text.charAt(text.length() - 1)) {
            return false;
        }

        return isPalindromeRecursive(text.substring(1, text.length() - 1));
    }

    public static boolean isPalindromeArrayReversal(String text) {
        char[] characters = text.toCharArray();

        int start = 0;
        int end = characters.length - 1;

        while (start < end) {
            char temporary = characters[start];
            characters[start] = characters[end];
            characters[end] = temporary;

            start++;
            end--;
        }

        String reversedText = new String(characters);

        return text.equals(reversedText);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a word: ");
        String text = scanner.nextLine();

        boolean iterativeResult = isPalindromeIterative(text);
        boolean recursiveResult = isPalindromeRecursive(text);
        boolean arrayResult = isPalindromeArrayReversal(text);

        System.out.println("\nIterative: " +
                (iterativeResult ? "Palindrome" : "Not Palindrome"));

        System.out.println("Recursive: " +
                (recursiveResult ? "Palindrome" : "Not Palindrome"));

        System.out.println("Array Reversal: " +
                (arrayResult ? "Palindrome" : "Not Palindrome"));

        scanner.close();
    }
}