package oopdesign.assigment_problems;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Question 2: The Assignment Submission Portal
 *
 * Classes and relationships:
 *   Assignment (abstract) <|-- CodingAssignment, WrittenAssignment
 *   Student 1 ---- 0..* Submission ---- 1 Assignment
 *
 * Each assignment type defines its own late-penalty rate. The grading
 * workflow only calls Assignment.applyLatePenalty(), so a new assignment
 * type needs no change to it. Assignment owns the due date, so it is the
 * class that works out how many days late a submission is. A Submission's
 * status is private and only moves SUBMITTED -> GRADED, through grade().
 */
public class AssignmentSubmissionPortal {

    private static String formatMarks(double marks) {
        if (marks == Math.floor(marks)) {
            return String.valueOf((long) marks);
        }
        return String.format("%.1f", marks);
    }

    // ---------- Assignment hierarchy ----------

    static abstract class Assignment {
        private final String title;
        private final int maxMarks;
        private final LocalDate dueDate;

        Assignment(String title, int maxMarks, LocalDate dueDate) {
            this.title = title;
            this.maxMarks = maxMarks;
            this.dueDate = dueDate;
        }

        // Each assignment type supplies only its own rate
        abstract int getPenaltyPercentPerDay();

        String getTitle() {
            return title;
        }

        int getMaxMarks() {
            return maxMarks;
        }

        // Assignment owns the due date, so it owns the lateness calculation
        long daysLate(LocalDate submittedOn) {
            long days = ChronoUnit.DAYS.between(dueDate, submittedOn);
            return Math.max(days, 0);
        }

        int getPenaltyPercent(long daysLate) {
            return (int) Math.min(100, getPenaltyPercentPerDay() * daysLate);
        }

        // Common penalty logic - identical for every assignment type
        double applyLatePenalty(double awardedMarks, long daysLate) {
            return awardedMarks * (100 - getPenaltyPercent(daysLate)) / 100.0;
        }
    }

    static class CodingAssignment extends Assignment {
        CodingAssignment(String title, int maxMarks, LocalDate dueDate) {
            super(title, maxMarks, dueDate);
        }

        @Override
        int getPenaltyPercentPerDay() {
            return 10;
        }
    }

    static class WrittenAssignment extends Assignment {
        WrittenAssignment(String title, int maxMarks, LocalDate dueDate) {
            super(title, maxMarks, dueDate);
        }

        @Override
        int getPenaltyPercentPerDay() {
            return 20;
        }
    }

    // ---------- Student and Submission ----------

    static class Student {
        private final String name;

        Student(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    static class Submission {
        enum Status {
            SUBMITTED("Submitted"),
            GRADED("Graded");

            private final String label;

            Status(String label) {
                this.label = label;
            }

            String getLabel() {
                return label;
            }
        }

        private final Student student;
        private final Assignment assignment;
        private final LocalDate submittedOn;
        private Status status = Status.SUBMITTED;
        private double finalMarks;

        Submission(Student student, Assignment assignment, LocalDate submittedOn) {
            this.student = student;
            this.assignment = assignment;
            this.submittedOn = submittedOn;
        }

        Student getStudent() {
            return student;
        }

        Assignment getAssignment() {
            return assignment;
        }

        Status getStatus() {
            return status;
        }

        long getDaysLate() {
            return assignment.daysLate(submittedOn);
        }

        // Only a SUBMITTED submission can be graded; once GRADED it is final
        boolean grade(double awardedMarks) {
            if (status == Status.GRADED) {
                return false;
            }
            finalMarks = assignment.applyLatePenalty(awardedMarks, getDaysLate()); // polymorphic penalty rule
            status = Status.GRADED;
            return true;
        }

        double getFinalMarks() {
            return finalMarks;
        }
    }

    // ---------- Portal workflow ----------

    static class Portal {
        private final List<Submission> submissions = new ArrayList<>();

        private Submission find(Student student, Assignment assignment) {
            for (Submission s : submissions) {
                if (s.getStudent() == student && s.getAssignment() == assignment) {
                    return s;
                }
            }
            return null;
        }

        Submission submit(Student student, Assignment assignment, LocalDate submittedOn) {
            Submission existing = find(student, assignment);
            if (existing != null && existing.getStatus() == Submission.Status.GRADED) {
                System.out.println("Cannot resubmit: '" + assignment.getTitle() + "' has already been graded.");
                return null;
            }
            if (existing != null) {
                submissions.remove(existing); // resubmission before grading replaces the old one
            }

            Submission submission = new Submission(student, assignment, submittedOn);
            submissions.add(submission);

            long late = submission.getDaysLate();
            String timing = (late == 0) ? "on time" : late + (late == 1 ? " day late" : " days late");
            System.out.println(student.getName() + "'s submission for '" + assignment.getTitle()
                    + "' received (" + timing + "). Status: " + submission.getStatus().getLabel() + ".");
            return submission;
        }

        void grade(Student student, Assignment assignment, double awardedMarks) {
            Submission submission = find(student, assignment);
            if (submission == null) {
                System.out.println("No submission from " + student.getName() + " for '"
                        + assignment.getTitle() + "' to grade.");
                return;
            }
            if (awardedMarks < 0 || awardedMarks > assignment.getMaxMarks()) {
                System.out.println("Marks must be between 0 and " + assignment.getMaxMarks() + ".");
                return;
            }
            if (!submission.grade(awardedMarks)) {
                System.out.println("'" + assignment.getTitle() + "' has already been graded.");
                return;
            }

            long late = submission.getDaysLate();
            String penaltyNote = (late == 0) ? ""
                    : " after " + assignment.getPenaltyPercent(late) + "% late penalty";
            System.out.println(student.getName() + " graded: " + formatMarks(submission.getFinalMarks())
                    + "/" + assignment.getMaxMarks() + penaltyNote + ". Status: "
                    + submission.getStatus().getLabel() + ".");
        }
    }

    public static void main(String[] args) {
        Portal portal = new Portal();

        Assignment linkedListLab = new CodingAssignment("Linked List Lab", 50, LocalDate.of(2027, 3, 10));
        Assignment designEssay = new WrittenAssignment("Design Essay", 50, LocalDate.of(2027, 3, 12));

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");

        portal.submit(asha, linkedListLab, LocalDate.of(2027, 3, 10));
        portal.submit(ravi, designEssay, LocalDate.of(2027, 3, 14));

        portal.grade(asha, linkedListLab, 45);
        portal.grade(ravi, designEssay, 40);

        portal.submit(asha, linkedListLab, LocalDate.of(2027, 3, 11)); // rejected - already graded
    }
}