package accessmodifiers.class_problems;

/**
 * PROBLEM 5: Immutable Booking Receipt & Nightly Settlement (part 1)
 * BookingReceipt is genuinely immutable: fields are final, the class is
 * final, and seatNumbers is defensively copied both going in (constructor)
 * and going out (getter) so no external code can mutate internal state.
 * "Changing" a receipt returns a brand-new object (the "wither" pattern).
 *
 * Note: the class is intentionally NOT declared final here, because
 * GroupBookingReceipt needs to extend it so that processNightlySettlement()
 * can accept both kinds through a single BookingReceipt[] array and tell
 * them apart with instanceof. Immutability is still fully enforced through
 * private final fields plus defensive copying in the constructor and getter -
 * that's the part that actually prevents external mutation.
 */
public class BookingReceipt {

    private final String bookingId;
    private final String[] seatNumbers;

    public BookingReceipt(String bookingId, String[] seatNumbers) {
        this.bookingId = bookingId;
        this.seatNumbers = seatNumbers.clone(); // defensive copy IN
    }

    public String getBookingId() {
        return bookingId;
    }

    public String[] getSeatNumbers() {
        return seatNumbers.clone(); // defensive copy OUT
    }

    public BookingReceipt withUpdatedSeat(int index, String newSeat) {
        String[] updatedSeats = seatNumbers.clone();
        updatedSeats[index] = newSeat;
        return new BookingReceipt(bookingId, updatedSeats);
    }

    public static void main(String[] args) {
        BookingReceipt b = new BookingReceipt("CH-1001", new String[]{"A1", "A2"});

        String[] seats = b.getSeatNumbers();
        seats[0] = "X"; // mutating the returned array must not affect the receipt
        System.out.println(b.getSeatNumbers()[0]); // A1

        BookingReceipt updated = b.withUpdatedSeat(1, "A3");
        System.out.println(java.util.Arrays.toString(b.getSeatNumbers()));       // [A1, A2]
        System.out.println(java.util.Arrays.toString(updated.getSeatNumbers())); // [A1, A3]
    }
}