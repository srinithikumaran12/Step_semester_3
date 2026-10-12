package oopdesign.assigment_problems;

import java.util.Locale;

/**
 * Question 1: The Hostel Laundry Queue
 *
 * Classes and relationships:
 *   WashType (abstract) <|-- QuickWash, NormalWash, HeavyWash
 *   Student 1 ---- 0..* WashCycle
 *   WashingMachine 1 ---- 0..1 WashCycle (current)   (a machine runs at most one wash at a time)
 *   WashCycle ---- 1 WashType
 *
 * Each wash type defines its own duration and charge. LaundryService only
 * talks to the WashType abstraction, so a new type (e.g. Delicate) can be
 * added without touching the booking logic. A machine's busy/free status is
 * private and only changes through start() and complete().
 */
public class HostelLaundrySystem {

    private static final String RUPEE = "\u20B9";

    private static String money(double amount) {
        return RUPEE + String.format(Locale.ENGLISH, "%,.2f", amount);
    }

    // ---------- Wash types ----------

    static abstract class WashType {
        abstract String getName();

        abstract int getDurationMinutes();

        abstract double getCharge();
    }

    static class QuickWash extends WashType {
        @Override
        String getName() {
            return "Quick";
        }

        @Override
        int getDurationMinutes() {
            return 30;
        }

        @Override
        double getCharge() {
            return 20.0;
        }
    }

    static class NormalWash extends WashType {
        @Override
        String getName() {
            return "Normal";
        }

        @Override
        int getDurationMinutes() {
            return 45;
        }

        @Override
        double getCharge() {
            return 30.0;
        }
    }

    static class HeavyWash extends WashType {
        @Override
        String getName() {
            return "Heavy";
        }

        @Override
        int getDurationMinutes() {
            return 60;
        }

        @Override
        double getCharge() {
            return 45.0;
        }
    }

    // ---------- Student, WashCycle, WashingMachine ----------

    static class Student {
        private final String name;

        Student(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    static class WashCycle {
        private final Student student;
        private final WashingMachine machine;
        private final WashType washType;

        WashCycle(Student student, WashingMachine machine, WashType washType) {
            this.student = student;
            this.machine = machine;
            this.washType = washType;
        }

        Student getStudent() {
            return student;
        }

        WashingMachine getMachine() {
            return machine;
        }

        WashType getWashType() {
            return washType;
        }
    }

    static class WashingMachine {
        private final String id;
        private WashCycle currentCycle; // null means the machine is free

        WashingMachine(String id) {
            this.id = id;
        }

        String getId() {
            return id;
        }

        boolean isBusy() {
            return currentCycle != null;
        }

        // Returns the new cycle, or null if the machine is already busy
        WashCycle start(Student student, WashType washType) {
            if (isBusy()) {
                return null;
            }
            currentCycle = new WashCycle(student, this, washType);
            return currentCycle;
        }

        // Returns true if a running cycle was completed
        boolean complete() {
            if (!isBusy()) {
                return false;
            }
            currentCycle = null;
            return true;
        }
    }

    // ---------- Common booking workflow (never changes for new wash types) ----------

    static class LaundryService {

        WashCycle startWash(Student student, WashingMachine machine, WashType washType) {
            WashCycle cycle = machine.start(student, washType);
            if (cycle == null) {
                System.out.println("Machine " + machine.getId() + " is currently busy.");
                return null;
            }

            System.out.println(washType.getName() + " wash started on " + machine.getId()
                    + " for " + student.getName() + " (" + washType.getDurationMinutes()
                    + " min). Charge: " + money(washType.getCharge()) + ".");
            return cycle;
        }

        void completeWash(WashingMachine machine) {
            if (machine.complete()) {
                System.out.println(machine.getId() + " cycle completed. " + machine.getId() + " is now free.");
            } else {
                System.out.println(machine.getId() + " has no wash in progress.");
            }
        }
    }

    public static void main(String[] args) {
        LaundryService service = new LaundryService();

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");

        WashingMachine m1 = new WashingMachine("M1");
        WashingMachine m2 = new WashingMachine("M2");

        service.startWash(asha, m1, new QuickWash());
        service.startWash(ravi, m1, new HeavyWash()); // rejected - M1 is busy
        service.startWash(ravi, m2, new HeavyWash());
        service.completeWash(m1);
        service.startWash(neha, m1, new NormalWash());
    }
}