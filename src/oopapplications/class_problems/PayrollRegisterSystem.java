package oopapplications.class_problems;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PayrollRegisterSystem {

    private static String formatAmount(double amount) {
        if (amount == Math.floor(amount)) {
            return String.valueOf((long) amount);
        }
        return String.format(Locale.ENGLISH, "%.2f", amount);
    }

    // ---------- Staff hierarchy ----------

    static abstract class Staff {
        private final String name;

        Staff(String name) {
            this.name = name;
        }

        abstract String getType();

        abstract double calculatePay();

        String getName() {
            return name;
        }

        Payslip toPayslip() {
            return new Payslip(name, getType(), calculatePay()); // polymorphic pay calculation
        }
    }

    static class FullTimeStaff extends Staff {
        private final double monthlySalary;

        FullTimeStaff(String name, double monthlySalary) {
            super(name);
            this.monthlySalary = monthlySalary;
        }

        @Override
        String getType() {
            return "FullTime";
        }

        @Override
        double calculatePay() {
            return monthlySalary;
        }
    }

    static class PartTimeStaff extends Staff {
        private final double hoursWorked;
        private final double hourlyRate;

        PartTimeStaff(String name, double hoursWorked, double hourlyRate) {
            super(name);
            this.hoursWorked = hoursWorked;
            this.hourlyRate = hourlyRate;
        }

        @Override
        String getType() {
            return "PartTime";
        }

        @Override
        double calculatePay() {
            return hoursWorked * hourlyRate;
        }
    }

    static class InternStaff extends Staff {
        private final double stipend;

        InternStaff(String name, double stipend) {
            super(name);
            this.stipend = stipend;
        }

        @Override
        String getType() {
            return "Intern";
        }

        @Override
        double calculatePay() {
            return stipend;
        }
    }

    // ---------- Payslip ----------

    static class Payslip {
        private final String name;
        private final String type;
        private final double pay;

        Payslip(String name, String type, double pay) {
            this.name = name;
            this.type = type;
            this.pay = pay;
        }

        String getName() {
            return name;
        }

        double getPay() {
            return pay;
        }

        @Override
        public String toString() {
            return "Payslip[name=" + name + ", type=" + type + ", pay=" + formatAmount(pay) + "]";
        }
    }

    // ---------- Processing ----------

    static List<String> process(String[] lines) {
        List<String> output = new ArrayList<>();
        List<Staff> staffList = new ArrayList<>();

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            Staff staff = parseStaff(trimmed);
            if (staff == null) {
                output.add("Invalid record: " + trimmed);
            } else {
                staffList.add(staff);
            }
        }

        if (staffList.isEmpty()) {
            return output; // nobody to pay
        }

        // Every payslip goes into an array
        Payslip[] payslips = new Payslip[staffList.size()];
        for (int i = 0; i < payslips.length; i++) {
            payslips[i] = staffList.get(i).toPayslip();
        }

        double total = 0;
        int topIndex = 0;
        for (int i = 0; i < payslips.length; i++) {
            output.add(payslips[i].toString());
            total += payslips[i].getPay();
            if (payslips[i].getPay() > payslips[topIndex].getPay()) { // strict ">" keeps the earlier one on ties
                topIndex = i;
            }
        }

        output.add("Total " + formatAmount(total));
        output.add("top earner " + payslips[topIndex].getName());
        return output;
    }

    // Returns null for a malformed line
    private static Staff parseStaff(String line) {
        String[] parts = line.split("\\s+");
        try {
            if (parts[0].equalsIgnoreCase("FullTime") && parts.length == 3) {
                return new FullTimeStaff(parts[1], Double.parseDouble(parts[2]));
            }
            if (parts[0].equalsIgnoreCase("PartTime") && parts.length == 4) {
                return new PartTimeStaff(parts[1], Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
            }
            if (parts[0].equalsIgnoreCase("Intern") && parts.length == 3) {
                return new InternStaff(parts[1], Double.parseDouble(parts[2]));
            }
        } catch (NumberFormatException e) {
            return null;
        }
        return null;
    }

    public static void main(String[] args) {
        String[] input = {
                "FullTime Asha 50000",
                "PartTime Ravi 80 300",
                "Intern Neha 15000"
        };

        for (String line : process(input)) {
            System.out.println(line);
        }
        // Payslip[name=Asha, type=FullTime, pay=50000]
        // Payslip[name=Ravi, type=PartTime, pay=24000]
        // Payslip[name=Neha, type=Intern, pay=15000]
        // Total 89000
        // top earner Asha
    }
}