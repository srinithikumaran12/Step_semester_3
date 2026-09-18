package oopconcepts.class_problems;

/**
 * M2: Encapsulated Mess-Card Wallet
 * Scenario: A hostel mess-card top-up wallet must never be allowed to go
 * negative, and its balance must never be overwritten directly from
 * outside the class.
 */
public class MessWallet {

    private double balance;

    public MessWallet(double openingBalance) {
        if (openingBalance < 0) {
            System.out.println("Warning: negative opening balance, starting at 0");
            this.balance = 0;
        } else {
            this.balance = openingBalance;
        }
    }

    public void topUp(double amount) {
        if (amount <= 0) {
            System.out.println("Top-up rejected: amount must be positive");
            return;
        }
        balance += amount;
        System.out.println("Balance after top-up: " + balance);
    }

    public void deduct(double amount) {
        if (amount > balance) {
            System.out.println("Deduct rejected: insufficient balance");
            return;
        }
        balance -= amount;
        System.out.println("Balance after deduction: " + balance);
    }

    public double getBalance() {
        return balance;
    }

    public static void main(String[] args) {
        MessWallet wallet = new MessWallet(500);
        wallet.topUp(200);       // Balance after top-up: 700.0
        wallet.deduct(1000);     // Deduct rejected: insufficient balance
        System.out.println("Final balance: " + wallet.getBalance()); // Final balance: 700.0
    }
}