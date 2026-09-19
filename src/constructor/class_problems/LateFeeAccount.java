package constructor.class_problems;

/**
 * M3: Late Fees - Skip the On-Time Accounts
 * Scenario: Late fees calculated for a batch of accounts - but only for
 * accounts that are genuinely late. calculateLateFee() and printSummary()
 * must both be final, locked against ever being overridden by a subclass.
 */
public class LateFeeAccount {

    String regNo;
    double totalFee;

    public LateFeeAccount(String regNo, double totalFee) {
        this.regNo = regNo;
        this.totalFee = totalFee;
    }

    final double calculateLateFee(int daysLate) {
        // 1% of total fee per day late
        return totalFee * daysLate / 100.0;
    }

    final void printSummary(int daysLate) {
        if (daysLate <= 0) {
            System.out.println(regNo + " - On time, no late fee");
            return;
        }
        double lateFee = calculateLateFee(daysLate);
        System.out.println(regNo + " | Total Fee: Rs " + totalFee + " | Late Fee: Rs " + lateFee);
    }

    public static void main(String[] args) {
        String[] regNos = {"RA001", "RA002", "RA003", "RA004"};
        double[] totalFees = {200000, 150000, 180000, 220000};
        int[] daysLate = {10, 0, -2, 5};

        for (int i = 0; i < regNos.length; i++) {
            LateFeeAccount account = new LateFeeAccount(regNos[i], totalFees[i]);
            account.printSummary(daysLate[i]);
        }
        // RA001 | Total Fee: Rs 200000.0 | Late Fee: Rs 20000.0
        // RA002 - On time, no late fee
        // RA003 - On time, no late fee
        // RA004 | Total Fee: Rs 220000.0 | Late Fee: Rs 11000.0
    }
}