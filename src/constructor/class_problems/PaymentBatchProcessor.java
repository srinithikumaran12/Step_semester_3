package constructor.class_problems;

/**
 * M5: Account Batch Payments (part 3 - batch processor)
 * Scenario: The finance office's nightly batch run has to process a whole
 * list of accounts in one pass - some plain FeeAccount, some
 * HostelFeeAccount - and each type pays differently. processPayment()
 * uses instanceof to dispatch between the two.
 */
public class PaymentBatchProcessor {

    int hostelCount = 0;
    int dayScholarCount = 0;

    void processPayment(FeeAccount account, double amount) {
        if (account instanceof HostelFeeAccount) {
            hostelCount++;
            System.out.println("Paid in two installments (hostel account)");
        } else {
            dayScholarCount++;
            System.out.println("Paid in one go (day-scholar account)");
        }
    }

    void printSummary() {
        System.out.println("Hostel accounts processed: " + hostelCount
                + " | Day-scholar accounts processed: " + dayScholarCount);
    }

    public static void main(String[] args) {
        FeeAccount[] accounts = {
                new HostelFeeAccount("H-1"),
                new HostelFeeAccount("H-2"),
                new FeeAccount("D-1"),
                new FeeAccount("D-2")
        };

        PaymentBatchProcessor processor = new PaymentBatchProcessor();
        for (FeeAccount account : accounts) {
            processor.processPayment(account, 60000);
        }
        processor.printSummary();
        // Paid in two installments (hostel account)
        // Paid in two installments (hostel account)
        // Paid in one go (day-scholar account)
        // Paid in one go (day-scholar account)
        // Hostel accounts processed: 2 | Day-scholar accounts processed: 2
    }
}