package oopdesign.assigment_problems;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Question 5: The Campus Notice Broadcaster
 *
 * Classes and relationships:
 *   NotificationChannel (interface) <|.. EmailChannel, SmsChannel, AppChannel
 *   Student 1 ---- 1..* NotificationChannel   (a student's preferred channels)
 *   NoticeBoard 1 ---- 0..* Student
 *   NoticeBoard ..> Notice                    (posts notices)
 *
 * NoticeBoard depends only on the NotificationChannel interface, so a new
 * channel (e.g. WhatsApp) needs no change to posting or delivery. Notice
 * validates itself: a Notice without a title or without a target
 * department cannot be created at all.
 */
public class CampusNoticeBroadcaster {

    private static final String ARROW = "\u2192";

    // ---------- Channels ----------

    interface NotificationChannel {
        void send(Student student, String message);
    }

    static class EmailChannel implements NotificationChannel {
        @Override
        public void send(Student student, String message) {
            System.out.println("[Email " + ARROW + " " + student.getName() + "] " + message + ".");
        }
    }

    static class SmsChannel implements NotificationChannel {
        @Override
        public void send(Student student, String message) {
            System.out.println("[SMS " + ARROW + " " + student.getName() + "] " + message + ".");
        }
    }

    static class AppChannel implements NotificationChannel {
        @Override
        public void send(Student student, String message) {
            System.out.println("[App " + ARROW + " " + student.getName() + "] " + message + ".");
        }
    }

    // ---------- Student ----------

    static class Student {
        private final String name;
        private final String department;
        private final List<NotificationChannel> preferredChannels = new ArrayList<>();

        Student(String name, String department) {
            this.name = name;
            this.department = department.trim().toUpperCase();
        }

        void addPreferredChannel(NotificationChannel channel) {
            preferredChannels.add(channel);
        }

        String getName() {
            return name;
        }

        String getDepartment() {
            return department;
        }

        List<NotificationChannel> getPreferredChannels() {
            return new ArrayList<>(preferredChannels); // defensive copy
        }
    }

    // ---------- Notice (validates itself) ----------

    static class Notice {
        private final String title;
        private final Set<String> targetDepartments = new LinkedHashSet<>();

        Notice(String title, List<String> departments) {
            if (title == null || title.trim().isEmpty()) {
                throw new IllegalArgumentException("A title is required.");
            }
            if (departments != null) {
                for (String dept : departments) {
                    if (dept != null && !dept.trim().isEmpty()) {
                        targetDepartments.add(dept.trim().toUpperCase());
                    }
                }
            }
            if (targetDepartments.isEmpty()) {
                throw new IllegalArgumentException("At least one target department is required.");
            }
            this.title = title.trim();
        }

        String getTitle() {
            return title;
        }

        boolean targets(String department) {
            return targetDepartments.contains(department);
        }

        String departmentList() {
            return String.join(", ", targetDepartments);
        }
    }

    // ---------- NoticeBoard (posting + delivery workflow) ----------

    static class NoticeBoard {
        private final List<Student> students = new ArrayList<>();

        void register(Student student) {
            students.add(student);
        }

        void post(String title, List<String> departments) {
            Notice notice;
            try {
                notice = new Notice(title, departments);
            } catch (IllegalArgumentException e) {
                System.out.println("Cannot post notice: " + e.getMessage());
                return;
            }

            System.out.println("Notice '" + notice.getTitle() + "' posted to " + notice.departmentList() + ".");

            for (Student student : students) {
                if (notice.targets(student.getDepartment())) {
                    for (NotificationChannel channel : student.getPreferredChannels()) {
                        channel.send(student, notice.getTitle()); // polymorphic delivery
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        NoticeBoard board = new NoticeBoard();

        Student asha = new Student("Asha", "CSE");
        asha.addPreferredChannel(new EmailChannel());
        asha.addPreferredChannel(new AppChannel());

        Student ravi = new Student("Ravi", "ECE");
        ravi.addPreferredChannel(new SmsChannel());

        board.register(asha);
        board.register(ravi);

        board.post("Lab Closed Tomorrow", List.of("CSE"));
        board.post("Fee Deadline Extended", List.of("CSE", "ECE"));
        board.post("Sports Day", List.of()); // rejected - no target department
    }
}