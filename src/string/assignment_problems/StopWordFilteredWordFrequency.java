package string.assigment_problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Assignment 5: Stop-Word-Filtered Word Frequency Report
 * Scenario: Word-frequency analysis of feedback paragraphs, excluding
 * common filler words so the report highlights meaningful themes.
 */
public class StopWordFilteredWordFrequency {

    private static final Set<String> STOP_WORDS = new HashSet<>(
            Arrays.asList("the", "was", "and", "a", "is", "of", "in"));

    void printFilteredWordFrequency(String feedback) {
        String cleaned = feedback.toLowerCase()
                .replace(",", "")
                .replace(".", "");

        String[] words = cleaned.split("\\s+");

        // LinkedHashMap preserves first-seen order, so ties print in the
        // same order they first appeared in the text.
        Map<String, Integer> frequency = new LinkedHashMap<>();

        for (String word : words) {
            if (STOP_WORDS.contains(word) || word.isEmpty()) {
                continue;
            }
            frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        }

        List<Map.Entry<String, Integer>> entries = new ArrayList<>(frequency.entrySet());

        // Collections.sort is stable, so ties keep their original (first-seen) order
        Collections.sort(entries, (a, b) -> b.getValue() - a.getValue());

        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    public static void main(String[] args) {
        StopWordFilteredWordFrequency report = new StopWordFilteredWordFrequency();

        report.printFilteredWordFrequency("The mentor was great, the session was great and clear.");
        // great: 2
        // mentor: 1
        // session: 1
        // clear: 1
    }
}