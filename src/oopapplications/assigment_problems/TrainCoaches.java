package oopapplications.assigment_problems;


public class TrainCoaches {

    static class Coach {
        String name;
        Coach prev;
        Coach next;

        Coach(String name) {
            this.name = name;
        }
    }

    static class Train {
        private Coach head; // engine end
        private Coach tail; // guard end

        void addLast(String name) {
            Coach coach = new Coach(name);
            if (tail == null) {   // empty train
                head = coach;
                tail = coach;
            } else {
                coach.prev = tail;
                tail.next = coach;
                tail = coach;
            }
        }

        // Returns true if a coach was removed, false if no coach has that name
        boolean remove(String name) {
            if (name == null) {
                return false;
            }

            Coach target = head;
            while (target != null && !target.name.equals(name)) {
                target = target.next;
            }
            if (target == null) {
                return false;
            }

            // Fix the neighbour before the target (or the head if there is none)
            if (target.prev != null) {
                target.prev.next = target.next;
            } else {
                head = target.next;
            }

            // Fix the neighbour after the target (or the tail if there is none)
            if (target.next != null) {
                target.next.prev = target.prev;
            } else {
                tail = target.prev;
            }

            target.prev = null; // detach the removed coach completely
            target.next = null;
            return true;
        }

        String forwardText() {
            if (head == null) {
                return "(empty)";
            }
            StringBuilder sb = new StringBuilder();
            for (Coach current = head; current != null; current = current.next) {
                sb.append(current.name);
                if (current.next != null) {
                    sb.append(" <-> ");
                }
            }
            return sb.toString();
        }

        String backwardText() {
            if (tail == null) {
                return "(empty)";
            }
            StringBuilder sb = new StringBuilder();
            for (Coach current = tail; current != null; current = current.prev) {
                sb.append(current.name);
                if (current.prev != null) {
                    sb.append(" <-> ");
                }
            }
            return sb.toString();
        }

        void printForward() {
            System.out.println("Forward: " + forwardText());
        }

        void printBackward() {
            System.out.println("Backward: " + backwardText());
        }
    }

    public static void main(String[] args) {
        Train train = new Train();
        for (String coach : new String[]{"Engine", "A1", "B1", "B2", "Guard"}) {
            train.addLast(coach);
        }

        if (!train.remove("B1")) {
            System.out.println("Coach not found: B1");
        }

        train.printForward();  // Forward: Engine <-> A1 <-> B2 <-> Guard
        train.printBackward(); // Backward: Guard <-> B2 <-> A1 <-> Engine
    }
}