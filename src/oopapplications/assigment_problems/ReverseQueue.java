package oopapplications.assigment_problems;


public class ReverseQueue {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    static Node reverse(Node head) {
        Node previous = null;
        Node current = head;

        while (current != null) {
            Node next = current.next; // remember the rest of the list
            current.next = previous;  // turn this node around
            previous = current;       // step forward
            current = next;
        }

        return previous; // the old last node is the new head
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
        System.out.println(toText(reverse(fromValues(1, 2, 3, 4)))); // 4 -> 3 -> 2 -> 1 -> null
        System.out.println(toText(reverse(fromValues(9))));          // 9 -> null
    }
}