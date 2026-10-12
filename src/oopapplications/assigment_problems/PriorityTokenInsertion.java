package oopapplications.assigment_problems;


public class PriorityTokenInsertion {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    static class TokenList {
        private Node head;
        private int size;

        // Builds a list from the given values, in order
        static TokenList of(int... tokens) {
            TokenList list = new TokenList();
            Node tail = null;
            for (int token : tokens) {
                Node node = new Node(token);
                if (tail == null) {
                    list.head = node;
                } else {
                    tail.next = node;
                }
                tail = node;
                list.size++;
            }
            return list;
        }

        // Priority token: goes to the front
        void addFirst(int token) {
            Node node = new Node(token);
            node.next = head;
            head = node;
            size++;
        }

        // Normal token: goes to the end
        void addLast(int token) {
            Node node = new Node(token);
            if (head == null) {
                head = node;
            } else {
                Node current = head;
                while (current.next != null) {
                    current = current.next;
                }
                current.next = node;
            }
            size++;
        }

        // Rescheduled token: goes to a 0-based position (0 <= index <= size)
        void insertAt(int index, int token) {
            if (index < 0 || index > size) {
                throw new IndexOutOfBoundsException("index " + index + " is outside 0.." + size);
            }
            if (index == 0) {
                addFirst(token);
                return;
            }

            Node previous = head;
            for (int i = 0; i < index - 1; i++) { // stop at the node just before the index
                previous = previous.next;
            }

            Node node = new Node(token);
            node.next = previous.next; // link the new node forward FIRST...
            previous.next = node;      // ...then link the previous node to it
            size++;
        }

        int size() {
            return size;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (Node current = head; current != null; current = current.next) {
                sb.append(current.data).append(" -> ");
            }
            return sb.append("null").toString();
        }
    }

    public static void main(String[] args) {
        TokenList list = TokenList.of(101, 102, 103);

        list.addFirst(100);
        list.addLast(104);
        list.insertAt(2, 150);

        System.out.println(list);
        // 100 -> 101 -> 150 -> 102 -> 103 -> 104 -> null
    }
}