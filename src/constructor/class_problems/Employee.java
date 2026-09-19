package constructor.class_problems;

/**
 * M2: Payroll Batch Bonus Round
 * Scenario: Payroll is giving every employee on a small team the same
 * festival bonus this month. The constructor and raiseSalary() must each
 * resolve a genuine field/parameter naming clash using `this`.
 */
public class Employee {

    String empId;
    double salary;

    public Employee(String empId, double salary) {
        this.empId = empId;
        this.salary = salary;
    }

    void raiseSalary(double salary) {
        this.salary = this.salary + salary;
    }

    public static void main(String[] args) {
        String[] empIds = {"E-101", "E-102", "E-103", "E-104"};
        double[] startingSalaries = {40000, 55000, 62000, 48000};

        Employee[] employees = new Employee[empIds.length];
        for (int i = 0; i < empIds.length; i++) {
            employees[i] = new Employee(empIds[i], startingSalaries[i]);
        }

        for (Employee employee : employees) {
            employee.raiseSalary(5000);
            System.out.println(employee.empId + " | Final Salary: Rs " + employee.salary);
        }
        // E-101 | Final Salary: Rs 45000.0
        // E-102 | Final Salary: Rs 60000.0
        // E-103 | Final Salary: Rs 67000.0
        // E-104 | Final Salary: Rs 53000.0
    }
}