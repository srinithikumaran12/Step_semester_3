package inheritance.assigment_problems;

import java.util.Arrays;

/**
 * PROBLEM 3: The Premium Loyalty Discount & Late-Fee Ledger
 */
public class Problem3LateFeeLedgerDemo {

    public static void main(String[] args) {
        PremiumMember p = new PremiumMember("MEM5", 2000, "Coach Riya");
        p.chargeLateFee(200); // halved via super.chargeLateFee(amount / 2)
        System.out.println(p.getTotalLateFees()); // 100

        int[] history = p.getLateFeeHistory();
        history[0] = 999; // tampering with the returned array...
        System.out.println(Arrays.toString(p.getLateFeeHistory())); // [100] - unaffected
    }
}