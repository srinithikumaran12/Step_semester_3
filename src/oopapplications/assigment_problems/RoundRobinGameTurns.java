package oopapplications.assigment_problems;


public class RoundRobinGameTurns {

    static class Player {
        String name;
        Player next;

        Player(String name) {
            this.name = name;
        }
    }

    static class PlayerCircle {
        private Player tail; // tail.next is the head (the first player)

        void addLast(String name) {
            Player player = new Player(name);
            if (tail == null) {
                player.next = player; // a single player points to itself
            } else {
                player.next = tail.next; // new last player points back to the head
                tail.next = player;      // old last player points to the new one
            }
            tail = player;
        }

        // The players for each turn, separated by spaces (empty if there are no players or turns)
        String turnsText(int turns) {
            if (tail == null || turns <= 0) {
                return "";
            }

            StringBuilder sb = new StringBuilder();
            Player current = tail.next; // start at the head
            for (int turn = 0; turn < turns; turn++) {
                sb.append(current.name);
                if (turn < turns - 1) {
                    sb.append(" ");
                }
                current = current.next; // wraps around by itself
            }
            return sb.toString();
        }

        void printTurns(int turns) {
            System.out.println(turnsText(turns));
        }
    }

    public static void main(String[] args) {
        PlayerCircle circle = new PlayerCircle();
        circle.addLast("Asha");
        circle.addLast("Ravi");
        circle.addLast("Neha");

        circle.printTurns(7);
        // Asha Ravi Neha Asha Ravi Neha Asha
    }
}