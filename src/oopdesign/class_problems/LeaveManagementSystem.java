package oopdesign.class_problems;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

/**
 * Question 2: Employee Leave Request Workflow
 *
 * Classes and relationships:
 *   Employee (abstract) <|-- FullTimeEmployee, PartTimeEmployee, Contractor
 *   Employee 1 ---- 0..* LeaveRequest
 *   Reviewer reviews LeaveRequest (association)
 *
 * Each employee type supplies its own leave rule (getMaxLeaveDays). The
 * submit/review workflow only depends on Employee, so a new employee type
 * needs no change to it. LeaveRequest owns its state transitions: only a
 * PENDING request can become APPROVED or REJECTED, and nothing can go
 * back to PENDING.
 */
public class LeaveManagementSystem {

    private static final DateTimeFormatter SHORT_DATE =
            DateTimeFormatter.ofPattern("MMM d", Locale.ENGLISH);

    // ---------- Status ----------

    enum LeaveStatus {
        PENDING("Pending"),
        APPROVED("Approved"),
        REJECTED("Rejected");

        private final String label;

        LeaveStatus(String label) {
            this.label = label;
        }

        String getLabel() {
            return label;
        }
    }

    // ---------- Employee hierarchy ----------

    static abstract class Employee {
        private final String name;

        Employee(String name) {
            this.name = name;
        }

        abstract int getMaxLeaveDays();

        String getName() {
            return name;
        }

        // Common workflow - identical for every employee type
        LeaveRequest submitLeave(LocalDate start, LocalDate end) {
            if (end.isBefore(start)) {
                System.out.println("Invalid leave dates for " + name + ": end is before start.");
                return null;
            }
            long days = ChronoUnit.DAYS.between(start, end) + 1;
            if (days > getMaxLeaveDays()) {
                System.out.println("Leave request for " + name + " exceeds the " + getMaxLeaveDays()
                        + "-day limit for this employee type.");
                return null;
            }

            LeaveRequest request = new LeaveRequest(this, start, end);
            System.out.println("Leave request submitted for " + name + " (" + request.describePeriod()
                    + "). Status: " + request.getStatus().getLabel() + ".");
            return request;
        }
    }

    static class FullTimeEmployee extends Employee {
        FullTimeEmployee(String name) {
            super(name);
        }

        @Override
        int getMaxLeaveDays() {
            return 20;
        }
    }

    static class PartTimeEmployee extends Employee {
        PartTimeEmployee(String name) {
            super(name);
        }

        @Override
        int getMaxLeaveDays() {
            return 5;
        }
    }

    static class Contractor extends Employee {
        Contractor(String name) {
            super(name);
        }

        @Override
        int getMaxLeaveDays() {
            return 3;
        }
    }

    // ---------- Leave request with guarded state transitions ----------

    static class LeaveRequest {
        private final Employee employee;
        private final LocalDate start;
        private final LocalDate end;
        private LeaveStatus status = LeaveStatus.PENDING;

        LeaveRequest(Employee employee, LocalDate start, LocalDate end) {
            this.employee = employee;
            this.start = start;
            this.end = end;
        }

        Employee getEmployee() {
            return employee;
        }

        LeaveStatus getStatus() {
            return status;
        }

        String describePeriod() {
            if (start.getMonth() == end.getMonth() && start.getYear() == end.getYear()) {
                return start.format(SHORT_DATE) + "-" + end.getDayOfMonth();
            }
            return start.format(SHORT_DATE) + " - " + end.format(SHORT_DATE);
        }

        boolean approve() {
            return changeStatus(LeaveStatus.APPROVED);
        }

        boolean reject() {
            return changeStatus(LeaveStatus.REJECTED);
        }

        boolean changeStatus(LeaveStatus newStatus) {
            if (status != LeaveStatus.PENDING) {
                System.out.println("Cannot change leave request status from " + status.getLabel()
                        + " to " + newStatus.getLabel() + ".");
                return false;
            }
            if (newStatus == LeaveStatus.PENDING) {
                return false; // already pending, nothing to do
            }
            status = newStatus;
            return true;
        }
    }

    // ---------- Reviewer ----------

    static class Reviewer {
        private final String name;

        Reviewer(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }

        void approve(LeaveRequest request) {
            if (request.approve()) {
                System.out.println(request.getEmployee().getName() + "'s leave request ("
                        + request.describePeriod() + ") approved. Status: "
                        + request.getStatus().getLabel() + ".");
            }
        }

        void reject(LeaveRequest request) {
            if (request.reject()) {
                System.out.println(request.getEmployee().getName() + "'s leave request ("
                        + request.describePeriod() + ") rejected. Status: "
                        + request.getStatus().getLabel() + ".");
            }
        }
    }

    public static void main(String[] args) {
        Employee john = new FullTimeEmployee("John");
        Employee jane = new PartTimeEmployee("Jane");
        Reviewer alice = new Reviewer("Alice");
        Reviewer bob = new Reviewer("Bob");

        LeaveRequest johnRequest = john.submitLeave(LocalDate.of(2027, 1, 1), LocalDate.of(2027, 1, 5));
        alice.approve(johnRequest);

        LeaveRequest janeRequest = jane.submitLeave(LocalDate.of(2027, 2, 10), LocalDate.of(2027, 2, 11));
        bob.reject(janeRequest);

        // John tries to move his approved request back to Pending
        johnRequest.changeStatus(LeaveStatus.PENDING);
    }
}