package inheritance.class_problems;

import java.util.Arrays;

/**
 * PROBLEM 3: The Student Discount & Fine Ledger
 */
public class Problem3FineLedgerDemo {

    public static void main(String[] args) {
        StudentMember s = new StudentMember("STU5", 3, "CSE");
        s.chargeFine(100); // halved via super.chargeFine(amount / 2)
        System.out.println(s.getTotalFine()); // 50

        int[] history = s.getFineHistory();
        history[0] = 999; // tampering with the returned array...
        System.out.println(Arrays.toString(s.getFineHistory())); // [50] - unaffected
    }
}