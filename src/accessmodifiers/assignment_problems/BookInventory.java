package accessmodifiers.assigment_problems;

/**
 * PROBLEM 3: Book Copy Circulation Guard
 * copiesTotal and copiesAvailable are both private, with no external way
 * to set copiesAvailable directly. checkOut() never lets the count go
 * negative; checkIn() never lets it exceed copiesTotal - both invalid
 * transitions are rejected silently.
 */
public class BookInventory {

    private int copiesTotal;
    private int copiesAvailable;

    public BookInventory(int copiesTotal) {
        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal;
    }

    void checkOut() {
        if (copiesAvailable <= 0) {
            return; // nothing left to check out, silently reject
        }
        copiesAvailable--;
    }

    void checkIn() {
        if (copiesAvailable >= copiesTotal) {
            return; // already at full capacity, nothing genuine to return
        }
        copiesAvailable++;
    }

    int getCopiesAvailable() {
        return copiesAvailable;
    }

    public static void main(String[] args) {
        BookInventory b = new BookInventory(3);
        b.checkOut();
        b.checkOut();
        b.checkOut();
        b.checkOut(); // 4th attempt, silently rejected
        System.out.println(b.getCopiesAvailable()); // 0

        b.checkIn();
        b.checkIn();
        b.checkIn();
        b.checkIn(); // 4th attempt, silently rejected (already full)
        System.out.println(b.getCopiesAvailable()); // 3
    }
}