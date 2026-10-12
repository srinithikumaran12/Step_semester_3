package oopapplications.class_problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ClubRegistrationSystem {

    static class Student {
        private final String rollNumber;
        private final String name;

        Student(String rollNumber, String name) {
            this.rollNumber = rollNumber;
            this.name = name;
        }

        String getRollNumber() {
            return rollNumber;
        }

        String getName() {
            return name;
        }

        // Equality is based on the roll number ONLY
        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Student)) {
                return false;
            }
            Student that = (Student) other;
            return rollNumber.equals(that.rollNumber);
        }

        // Must use the same field as equals(), so equal students land in the same bucket
        @Override
        public int hashCode() {
            return rollNumber.hashCode();
        }
    }

    static List<String> process(String[] lines) {
        Set<Student> members = new HashSet<>();
        List<String> output = new ArrayList<>();

        int lastAddIndex = -1;
        for (int i = 0; i < lines.length; i++) {
            if (isCommand(lines[i], "ADD")) {
                lastAddIndex = i;
            }
        }
        if (lastAddIndex == -1) {
            output.add("member count 0"); // no ADD operations at all
        }

        for (int i = 0; i < lines.length; i++) {
            String trimmed = lines[i].trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            String[] parts = trimmed.split("\\s+");

            if (parts[0].equalsIgnoreCase("ADD") && parts.length >= 3) {
                String name = String.join(" ", Arrays.copyOfRange(parts, 2, parts.length));
                boolean isNew = members.add(new Student(parts[1], name)); // false when already present
                output.add(isNew ? "Added" : "duplicate rejected");

                if (i == lastAddIndex) {
                    output.add("member count " + members.size());
                }

            } else if (parts[0].equalsIgnoreCase("CONTAINS") && parts.length >= 2) {
                String name = parts.length >= 3
                        ? String.join(" ", Arrays.copyOfRange(parts, 2, parts.length)) : "";
                output.add("contains: " + members.contains(new Student(parts[1], name)));

            } else {
                output.add("Invalid command: " + trimmed);
            }
        }
        return output;
    }

    private static boolean isCommand(String line, String command) {
        String[] parts = line.trim().split("\\s+");
        return parts.length >= 3 && parts[0].equalsIgnoreCase(command);
    }

    public static void main(String[] args) {
        String[] input = {
                "ADD 21CS01 Asha",
                "ADD 21CS01 Asha",
                "ADD 21CS02 Ravi",
                "CONTAINS 21CS02 Ravi",
                "CONTAINS 21CS03 Priya"
        };

        for (String line : process(input)) {
            System.out.println(line);
        }
        // Added
        // duplicate rejected
        // Added
        // member count 2
        // contains: true
        // contains: false
    }
}