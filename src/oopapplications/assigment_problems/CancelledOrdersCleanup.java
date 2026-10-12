package oopapplications.assigment_problems;


public class CancelledOrdersCleanup {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    static Node removeAll(Node head, int code) {
        // Step 1: drop matching nodes at the front
        while (head != null && head.data == code) {
            head = head.next;
        }
        if (head == null) {
            return null; // every node matched (or the list was empty)
        }

        // Step 2: head is now a keeper; skip matching nodes after it
        Node previous = head;
        while (previous.next != null) {
            if (previous.next.data == code) {
                previous.next = previous.next.next; // unlink; do NOT advance, the next one may match too
            } else {
                previous = previous.next;
            }
        }
        return head;
    }

    // Builds a list from the given values, in order (null for no values)
    static Node fromValues(int... values) {
        Node head = null;
        Node tail = null;
        for (int value : values) {
            Node node = new Node(value);
            if (tail == null) {
                head = node;
            } else {
                tail.next = node;
            }
            tail = node;
        }
        return head;
    }

    static String toText(Node head) {
        StringBuilder sb = new StringBuilder();
        for (Node current = head; current != null; current = current.next) {
            sb.append(current.data).append(" -> ");
        }
        return sb.append("null").toString();
    }

    public static void main(String[] args) {
        Node orders1 = fromValues(5, 3, 5, 8, 5);
        System.out.println(toText(removeAll(orders1, 5))); // 3 -> 8 -> null

        Node orders2 = fromValues(5, 5, 5);
        System.out.println(toText(removeAll(orders2, 5))); // null
    }
}