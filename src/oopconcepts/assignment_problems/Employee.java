package oopconcepts.assigment_problems;

/**
 * M3: Overloaded Constructors for an Employee
 * Scenario: Interns join without a fixed salary structure yet; permanent
 * employees join with a known salary from day one. Support both without
 * writing the same setup logic twice.
 */
public class Employee {

    String empId;
    String empName;
    double salary;
    boolean isIntern;

    // Permanent employees
    public Employee(String empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
        this.isIntern = false;
    }

    // Interns - chains to the permanent-employee constructor with salary 0,
    // then marks isIntern true afterwards
    public Employee(String empId, String empName) {
        this(empId, empName, 0);
        this.isIntern = true;
    }

    void printProfile() {
        System.out.println(empId + " | " + empName + " | Rs " + salary + " | Intern: " + isIntern);
    }

    public static void main(String[] args) {
        Employee permanent = new Employee("E-101", "Divya", 65000);
        Employee intern = new Employee("E-102", "Arjun");

        permanent.printProfile(); // E-101 | Divya | Rs 65000.0 | Intern: false
        intern.printProfile();    // E-102 | Arjun | Rs 0.0 | Intern: true
    }
}