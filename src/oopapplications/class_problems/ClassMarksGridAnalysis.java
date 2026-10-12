package oopapplications.class_problems;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ClassMarksGridAnalysis {

    static List<String> process(String[] lines) {
        List<String> names = new ArrayList<>();
        List<int[]> rows = new ArrayList<>();

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            parseStudent(trimmed, names, rows);
        }

        List<String> output = new ArrayList<>();
        if (names.isEmpty()) {
            return output; // no students, nothing to report
        }

        int students = names.size();
        int subjects = rows.get(0).length;

        int[][] grid = new int[students][];
        for (int i = 0; i < students; i++) {
            grid[i] = rows.get(i);
            if (grid[i].length != subjects) {
                throw new IllegalArgumentException("Every student must have " + subjects
                        + " subjects, but " + names.get(i) + " has " + grid[i].length);
            }
        }

        // Row totals + topper
        int[] totals = new int[students];
        int topperIndex = 0;
        for (int i = 0; i < students; i++) {
            for (int j = 0; j < subjects; j++) {
                totals[i] += grid[i][j];
            }
            if (totals[i] > totals[topperIndex]) { // strict ">" keeps the first student on ties
                topperIndex = i;
            }
        }

        // Column averages
        StringBuilder averages = new StringBuilder("averages ");
        for (int j = 0; j < subjects; j++) {
            int columnSum = 0;
            for (int i = 0; i < students; i++) {
                columnSum += grid[i][j];
            }
            averages.append(String.format(Locale.ENGLISH, "%.2f", columnSum / (double) students));
            if (j < subjects - 1) {
                averages.append(", ");
            }
        }

        StringBuilder totalsLine = new StringBuilder("Totals ");
        for (int i = 0; i < students; i++) {
            totalsLine.append(names.get(i)).append(" ").append(totals[i]);
            if (i < students - 1) {
                totalsLine.append(", ");
            }
        }

        output.add(totalsLine.toString());
        output.add(averages.toString());
        output.add("topper " + names.get(topperIndex) + " (" + totals[topperIndex] + ")");
        return output;
    }

    // Parses "Asha [78, 85, 90]" into a name and its marks
    private static void parseStudent(String line, List<String> names, List<int[]> rows) {
        int open = line.indexOf('[');
        int close = line.lastIndexOf(']');
        if (open <= 0 || close < open) {
            throw new IllegalArgumentException("Expected '<Name> [m1, m2, ...]' but got: " + line);
        }

        String name = line.substring(0, open).trim();
        String[] markTexts = line.substring(open + 1, close).split(",");

        int[] marks = new int[markTexts.length];
        for (int i = 0; i < markTexts.length; i++) {
            try {
                marks[i] = Integer.parseInt(markTexts[i].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Marks must be whole numbers in: " + line);
            }
        }

        names.add(name);
        rows.add(marks);
    }

    public static void main(String[] args) {
        String[] input = {
                "Asha [78, 85, 90]",
                "Ravi [88, 92, 79]",
                "Neha [65, 70, 95]"
        };

        for (String line : process(input)) {
            System.out.println(line);
        }
        // Totals Asha 253, Ravi 259, Neha 230
        // averages 77.00, 82.33, 88.00
        // topper Ravi (259)
    }
}