package string.class_problems;

/**
 * Problem 2: CSV Student Record Parser
 * Scenario: The T&P team receives student registration data as CSV lines
 * and needs a quick parser to split each line into fields and print
 * a formatted record.
 */
public class CSVStudentRecordParser {

    void parseStudentRecord(String csvLine) {
        String[] fields = csvLine.split(",");

        if (fields.length != 3) {
            System.out.println("Invalid Record");
            return;
        }

        String name = fields[0];
        String rollNumber = fields[1];
        String department = fields[2];

        System.out.println("Name: " + name + " | Roll No: " + rollNumber + " | Dept: " + department);
    }

    public static void main(String[] args) {
        CSVStudentRecordParser parser = new CSVStudentRecordParser();
        parser.parseStudentRecord("Ananya Verma,RA2211003010123,CSE");
        // Name: Ananya Verma | Roll No: RA2211003010123 | Dept: CSE

        parser.parseStudentRecord("Ananya Verma,CSE");
        // Invalid Record
    }
}