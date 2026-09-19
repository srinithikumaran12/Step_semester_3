package accessmodifiers.class_problems;

/**
 * PROBLEM 3: Seat Booking Encapsulation Guard
 * seatsTotal and seatsAvailable are both private, with no direct external
 * setter for seatsAvailable. The constructor rejects a nonsensical
 * seatsTotal, and bookSeat()/cancelBooking() silently reject any
 * transition that would push the count out of bounds.
 */
public class CineScreen {

    private int seatsTotal;
    private int seatsAvailable;

    public CineScreen(int seatsTotal) {
        if (seatsTotal <= 0) {
            System.out.println("construction rejected");
            this.seatsTotal = 0;
            this.seatsAvailable = 0;
            return;
        }
        this.seatsTotal = seatsTotal;
        this.seatsAvailable = seatsTotal;
    }

    void bookSeat() {
        if (seatsAvailable <= 0) {
            return; // nothing to book, silently reject
        }
        seatsAvailable--;
    }

    void cancelBooking() {
        if (seatsAvailable >= seatsTotal) {
            return; // nothing genuine to undo, silently reject
        }
        seatsAvailable++;
    }

    int getSeatsAvailable() {
        return seatsAvailable;
    }

    public static void main(String[] args) {
        CineScreen rejected = new CineScreen(0); // construction rejected

        CineScreen c = new CineScreen(2);
        c.bookSeat();
        c.bookSeat();
        c.bookSeat(); // 3rd booking rejected, no seat left
        System.out.println(c.getSeatsAvailable()); // 0

        c.cancelBooking();
        c.cancelBooking();
        c.cancelBooking(); // 3rd cancellation rejected, already full
        System.out.println(c.getSeatsAvailable()); // 2
    }
}