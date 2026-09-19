package accessmodifiers.class_problems;

/**
 * PROBLEM 5: Immutable Booking Receipt & Nightly Settlement (part 3)
 * Reconciles a night's worth of receipts, using instanceof to tell a
 * GroupBookingReceipt apart from a regular BookingReceipt, and never
 * throwing on a null batch entry.
 */
public class NightlySettlementProcessor {

    static String processNightlySettlement(BookingReceipt[] receipts) {
        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (BookingReceipt receipt : receipts) {
            if (receipt == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (receipt instanceof GroupBookingReceipt) {
                group++;
            } else {
                individual++;
            }
        }

        return processed + " processed | " + nullSkipped + " null skipped | "
                + group + " group | " + individual + " individual";
    }

    public static void main(String[] args) {
        BookingReceipt[] receipts = {
                new GroupBookingReceipt("CH-2002", new String[]{"B1", "B2"}, 2),
                null,
                new BookingReceipt("CH-3003", new String[]{"C1"})
        };

        System.out.println(processNightlySettlement(receipts));
        // 2 processed | 1 null skipped | 1 group | 1 individual
    }
}