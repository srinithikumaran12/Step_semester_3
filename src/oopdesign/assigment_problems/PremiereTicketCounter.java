package oopdesign.assigment_problems;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Question 3: The Campus Premiere Ticket Counter
 *
 * Classes and relationships:
 *   Seat (abstract) <|-- RegularSeat, PremiumSeat, ReclinerSeat
 *   Show 1 ---- 1..* Seat                (the show's seating layout)
 *   Customer 1 ---- 0..* Booking ---- 1 Show
 *   Booking 1 ---- 1..6 Seat
 *
 * Each seat category defines its own price. The booking total is just the
 * sum of getPrice() over the chosen seats, so a new category needs no
 * change to the booking logic. The Show tracks which seats are booked;
 * cancelling a booking releases all of its seats.
 */
public class PremiereTicketCounter {

    private static final int MAX_SEATS_PER_BOOKING = 6;
    private static final String RUPEE = "\u20B9";

    private static String money(double amount) {
        return RUPEE + String.format(Locale.ENGLISH, "%,.2f", amount);
    }

    // ---------- Seat hierarchy ----------

    static abstract class Seat {
        private final String id;

        Seat(String id) {
            this.id = id;
        }

        String getId() {
            return id;
        }

        abstract double getPrice();
    }

    static class RegularSeat extends Seat {
        RegularSeat(String id) {
            super(id);
        }

        @Override
        double getPrice() {
            return 150.0;
        }
    }

    static class PremiumSeat extends Seat {
        PremiumSeat(String id) {
            super(id);
        }

        @Override
        double getPrice() {
            return 250.0;
        }
    }

    static class ReclinerSeat extends Seat {
        ReclinerSeat(String id) {
            super(id);
        }

        @Override
        double getPrice() {
            return 400.0;
        }
    }

    // ---------- Customer ----------

    static class Customer {
        private final String name;

        Customer(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    // ---------- Show ----------

    static class Show {
        private final String name;
        private final LocalDateTime startTime;
        private final Map<String, Seat> seats = new LinkedHashMap<>();
        private final Set<String> bookedSeatIds = new HashSet<>();

        Show(String name, LocalDateTime startTime) {
            this.name = name;
            this.startTime = startTime;
        }

        void addSeat(Seat seat) {
            seats.put(seat.getId(), seat);
        }

        String getName() {
            return name;
        }

        LocalDateTime getStartTime() {
            return startTime;
        }

        Seat findSeat(String seatId) {
            return seats.get(seatId);
        }

        boolean isBooked(String seatId) {
            return bookedSeatIds.contains(seatId);
        }

        void reserve(Seat seat) {
            bookedSeatIds.add(seat.getId());
        }

        void release(Seat seat) {
            bookedSeatIds.remove(seat.getId());
        }
    }

    // ---------- Booking ----------

    static class Booking {
        private final Customer customer;
        private final Show show;
        private final List<Seat> seats;
        private boolean cancelled = false;

        Booking(Customer customer, Show show, List<Seat> seats) {
            this.customer = customer;
            this.show = show;
            this.seats = new ArrayList<>(seats); // defensive copy
        }

        Customer getCustomer() {
            return customer;
        }

        Show getShow() {
            return show;
        }

        List<Seat> getSeats() {
            return new ArrayList<>(seats); // defensive copy
        }

        boolean isCancelled() {
            return cancelled;
        }

        void markCancelled() {
            cancelled = true;
        }

        double getTotal() {
            double total = 0;
            for (Seat seat : seats) {
                total += seat.getPrice(); // polymorphic call
            }
            return total;
        }

        String seatList() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < seats.size(); i++) {
                sb.append(seats.get(i).getId());
                if (i < seats.size() - 1) {
                    sb.append(", ");
                }
            }
            return sb.toString();
        }
    }

    // ---------- Booking workflow ----------

    static class BookingDesk {

        // All-or-nothing: if any requested seat is unavailable, nothing is booked
        Booking book(Customer customer, Show show, String... seatIds) {
            if (seatIds.length == 0) {
                System.out.println("Select at least one seat.");
                return null;
            }
            if (seatIds.length > MAX_SEATS_PER_BOOKING) {
                System.out.println("A booking can have at most " + MAX_SEATS_PER_BOOKING + " seats.");
                return null;
            }

            List<Seat> chosen = new ArrayList<>();
            Set<String> seenInRequest = new HashSet<>();

            for (String seatId : seatIds) {
                Seat seat = show.findSeat(seatId);
                if (seat == null) {
                    System.out.println("Seat " + seatId + " does not exist for this show.");
                    return null;
                }
                if (!seenInRequest.add(seatId) || show.isBooked(seatId)) {
                    System.out.println("Seat " + seatId + " is already booked for this show.");
                    return null;
                }
                chosen.add(seat);
            }

            for (Seat seat : chosen) {
                show.reserve(seat);
            }

            Booking booking = new Booking(customer, show, chosen);
            System.out.println("Booking confirmed for " + customer.getName() + ": " + booking.seatList()
                    + ". Total: " + money(booking.getTotal()) + ".");
            return booking;
        }

        boolean cancel(Booking booking, LocalDateTime now) {
            if (booking.isCancelled()) {
                System.out.println("This booking is already cancelled.");
                return false;
            }
            if (!now.isBefore(booking.getShow().getStartTime())) {
                System.out.println("Cannot cancel: the show has already started.");
                return false;
            }

            for (Seat seat : booking.getSeats()) {
                booking.getShow().release(seat);
            }
            booking.markCancelled();
            System.out.println(booking.getCustomer().getName() + "'s booking cancelled. Seats "
                    + booking.seatList() + " released.");
            return true;
        }
    }

    public static void main(String[] args) {
        BookingDesk desk = new BookingDesk();

        LocalDateTime showStart = LocalDateTime.of(2027, 3, 15, 19, 0); // 7 PM show
        LocalDateTime now = LocalDateTime.of(2027, 3, 15, 15, 0);       // before the show starts

        Show sevenPm = new Show("7 PM show", showStart);
        sevenPm.addSeat(new RegularSeat("A1"));
        sevenPm.addSeat(new RegularSeat("A2"));
        sevenPm.addSeat(new PremiumSeat("F5"));
        sevenPm.addSeat(new ReclinerSeat("R1"));

        Customer asha = new Customer("Asha");
        Customer ravi = new Customer("Ravi");
        Customer neha = new Customer("Neha");

        Booking ashaBooking = desk.book(asha, sevenPm, "A1", "A2", "F5");
        desk.book(ravi, sevenPm, "A2");        // rejected - already booked
        desk.book(ravi, sevenPm, "R1");
        desk.cancel(ashaBooking, now);         // before the show starts
        desk.book(neha, sevenPm, "A2");
    }
}