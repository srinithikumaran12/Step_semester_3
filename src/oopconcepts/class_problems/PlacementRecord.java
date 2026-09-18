package oopconcepts.class_problems;

/**
 * M1: From Parallel Arrays to a Class
 * Scenario: The T&P cell currently tracks student placements using three
 * parallel arrays - names, companies, packages - and it has already gone
 * out of sync twice this month. Rebuild it the OOP way.
 */
public class PlacementRecord {

    String studentName;
    String company;
    double packageLpa;

    public PlacementRecord(String studentName, String company, double packageLpa) {
        this.studentName = studentName;
        this.company = company;
        this.packageLpa = packageLpa;
    }

    void printRecord() {
        System.out.println(studentName + " -> " + company + " @ " + packageLpa + " LPA");
    }

    public static void main(String[] args) {
        PlacementRecord[] records = new PlacementRecord[3];
        records[0] = new PlacementRecord("Ravi", "TCS", 4.5);
        records[1] = new PlacementRecord("Anitha", "Zoho", 6.2);
        records[2] = new PlacementRecord("Karthik", "Infosys", 4.0);

        for (PlacementRecord record : records) {
            record.printRecord();
        }
        // Ravi -> TCS @ 4.5 LPA
        // Anitha -> Zoho @ 6.2 LPA
        // Karthik -> Infosys @ 4.0 LPA
    }
}