package accessmodifiers.assigment_problems;

/**
 * PROBLEM 5: Immutable Loan Receipt & Nightly Circulation Ledger (part 3)
 * Sets up one-time, class-level state via a static block, then reconciles
 * a night's worth of receipts - using instanceof to settle a
 * ReferenceOnlyLoanReceipt differently, and never throwing on a null entry.
 */
public class CirculationLedger {

    static String branchCode;

    static {
        branchCode = "PT-MAIN";
        System.out.println("Circulation ledger initialized for branch: " + branchCode);
    }

    static String processNightlyCirculation(LoanReceipt[] receipts) {
        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;

        for (LoanReceipt receipt : receipts) {
            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                referenceOnly++;
            } else {
                regular++;
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | "
                + referenceOnly + " reference-only | " + regular + " regular";
    }

    public static void main(String[] args) {
        LoanReceipt[] receipts = {
                new ReferenceOnlyLoanReceipt("LIB-001", new String[]{"BK-200"}, "Reading Room 3"),
                null,
                new LoanReceipt("LIB-002", new String[]{"BK-201"})
        };

        System.out.println(processNightlyCirculation(receipts));
        // 2 processed | 1 null skipped | 1 reference-only | 1 regular
    }
}