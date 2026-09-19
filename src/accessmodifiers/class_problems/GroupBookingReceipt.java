package accessmodifiers.class_problems;

/**
 * PROBLEM 5: Immutable Booking Receipt & Nightly Settlement (part 2)
 * A group-booking variant of BookingReceipt, adding a groupSize field.
 * Also immutable: groupSize is final and set once through the constructor.
 */
public class GroupBookingReceipt extends BookingReceipt {

    private final int groupSize;

    public GroupBookingReceipt(String bookingId, String[] seatNumbers, int groupSize) {
        super(bookingId, seatNumbers);
        this.groupSize = groupSize;
    }

    public int getGroupSize() {
        return groupSize;
    }
}