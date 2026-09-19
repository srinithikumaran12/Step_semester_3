package oopconcepts.assigment_problems;

/**
 * M2: Encapsulated Payroll Account
 * Scenario: A company's payroll account must never let its net salary be
 * set directly from outside the class, and bonuses must never be negative.
 */
public class PayrollAccount {

    private double basicSalary;
    private double bonus;

    public PayrollAccount(double openingBasicSalary) {
        if (openingBasicSalary < 0) {
            System.out.println("Warning: negative opening salary, starting at 0");
            this.basicSalary = 0;
        } else {
            this.basicSalary = openingBasicSalary;
        }
        this.bonus = 0;
    }

    public void creditBonus(double amount) {
        if (amount <= 0) {
            System.out.println("Bonus credit rejected: amount must be positive");
            return;
        }
        bonus += amount;
        System.out.println("Bonus credited: Rs " + amount);
    }

    public void deductTax(double percent) {
        if (percent < 0 || percent > 100) {
            System.out.println("Tax deduction rejected: percent must be between 0 and 100");
            return;
        }
        basicSalary -= basicSalary * (percent / 100);
        System.out.println("Tax deducted: " + (int) percent + "%");
    }

    public double getNetSalary() {
        return basicSalary + bonus;
    }

    public static void main(String[] args) {
        PayrollAccount account = new PayrollAccount(50000);
        account.creditBonus(5000);  // Bonus credited: Rs 5000.0
        account.deductTax(10);      // Tax deducted: 10%
        System.out.println("Net salary: Rs " + account.getNetSalary()); // Net salary: Rs 50000.0
    }
}