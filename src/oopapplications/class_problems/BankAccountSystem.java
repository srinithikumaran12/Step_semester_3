package oopapplications.class_problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BankAccountSystem {

    private static final long SAVINGS_MINIMUM_BALANCE = 1000;
    private static final long CURRENT_OVERDRAFT_LIMIT = 5000;

    // ---------- Account hierarchy ----------

    static abstract class Account {
        private final String id;
        private long balance;

        Account(String id, long initialBalance) {
            this.id = id;
            this.balance = initialBalance;
        }

        abstract long getLowestAllowedBalance();

        abstract String getRejectionReason();

        String getId() {
            return id;
        }

        long getBalance() {
            return balance;
        }

        // Returns true if the withdrawal was made, false if it would break the account's rule
        boolean withdraw(long amount) {
            if (balance - amount < getLowestAllowedBalance()) {
                return false;
            }
            balance -= amount;
            return true;
        }
    }

    static class SavingsAccount extends Account {
        SavingsAccount(String id, long initialBalance) {
            super(id, initialBalance);
        }

        @Override
        long getLowestAllowedBalance() {
            return SAVINGS_MINIMUM_BALANCE;
        }

        @Override
        String getRejectionReason() {
            return "minimum balance " + SAVINGS_MINIMUM_BALANCE;
        }
    }

    static class CurrentAccount extends Account {
        CurrentAccount(String id, long initialBalance) {
            super(id, initialBalance);
        }

        @Override
        long getLowestAllowedBalance() {
            return -CURRENT_OVERDRAFT_LIMIT;
        }

        @Override
        String getRejectionReason() {
            return "overdraft limit " + CURRENT_OVERDRAFT_LIMIT;
        }
    }

    // ---------- Processing ----------

    // Creating an account prints nothing; every WITHDRAW prints exactly one line.
    // Re-creating an existing ID is ignored, so the original account is kept.
    static List<String> process(String[] lines) {
        Map<String, Account> accounts = new HashMap<>();
        List<String> output = new ArrayList<>();

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            String[] parts = trimmed.split("\\s+");
            String command = parts[0];

            try {
                if (command.equalsIgnoreCase("Savings") && parts.length == 3) {
                    accounts.putIfAbsent(parts[1], new SavingsAccount(parts[1], Long.parseLong(parts[2])));

                } else if (command.equalsIgnoreCase("Current") && parts.length == 3) {
                    accounts.putIfAbsent(parts[1], new CurrentAccount(parts[1], Long.parseLong(parts[2])));

                } else if (command.equalsIgnoreCase("WITHDRAW") && parts.length == 3) {
                    output.add(withdraw(accounts, parts[1], Long.parseLong(parts[2])));

                } else {
                    output.add("Invalid command: " + trimmed);
                }
            } catch (NumberFormatException e) {
                output.add("Invalid amount in: " + trimmed);
            }
        }
        return output;
    }

    private static String withdraw(Map<String, Account> accounts, String id, long amount) {
        Account account = accounts.get(id);
        if (account == null) {
            return "Account not found";
        }
        if (amount <= 0) {
            return id + " rejected: invalid amount";
        }
        if (account.withdraw(amount)) { // polymorphic rule check
            return id + " balance " + account.getBalance();
        }
        return id + " rejected: " + account.getRejectionReason();
    }

    public static void main(String[] args) {
        String[] input = {
                "Savings S1 5000",
                "WITHDRAW S1 4500",
                "Current C1 2000",
                "WITHDRAW C1 6000",
                "WITHDRAW S1 3000",
                "WITHDRAW C1 2000"
        };

        for (String line : process(input)) {
            System.out.println(line);
        }
        // S1 rejected: minimum balance 1000
        // C1 balance -4000
        // S1 balance 2000
        // C1 rejected: overdraft limit 5000
    }
}