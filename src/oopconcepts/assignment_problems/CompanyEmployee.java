package oopconcepts.assigment_problems;

/**
 * M5: Instance vs Static - Splitting an Employee Class Correctly
 * Scenario: A trainee developer's first draft of Employee stores empName,
 * salary, AND the company name as instance fields - meaning every employee
 * ends up with its own copy of "Bright Horizon Technologies" typed in
 * separately. Fix the design.
 *
 * Note: named CompanyEmployee here (instead of Employee) to avoid a name
 * clash with the Employee class already defined for M3 in this same
 * package/branch.
 */
public class CompanyEmployee {

    String empName;
    double salary;

    static String companyName = "Bright Horizon Technologies";
    static int employeeCount = 0;

    public CompanyEmployee(String empName, double salary) {
        this.empName = empName;
        this.salary = salary;
        employeeCount++;
    }

    static void printCompanyInfo() {
        System.out.println(companyName);
        System.out.println("Employees on record: " + employeeCount);
    }

    public static void main(String[] args) {
        CompanyEmployee e1 = new CompanyEmployee("Divya", 65000);
        CompanyEmployee e2 = new CompanyEmployee("Arjun", 40000);
        CompanyEmployee e3 = new CompanyEmployee("Meera", 55000);

        CompanyEmployee.printCompanyInfo();
        // Bright Horizon Technologies
        // Employees on record: 3
    }
}