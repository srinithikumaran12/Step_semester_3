package oopdesign.class_problems;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Question 4: Hotel Booking System
 *
 * Classes and relationships:
 *   Room (abstract) <|-- StandardRoom, DeluxeRoom, Suite
 *   Room 1 ---- 0..* Reservation          (a room can have many reservations over time,
 *                                          but never two ACTIVE ones that overlap)
 *   Customer 1 ---- 0..* Reservation
 *
 * Each room category implements its own calculatePrice(). Availability is
 * decided by checking the room's active reservations for date overlap.
 * A reservation can be cancelled only up to 2 days before check-in.
 */
public class HotelBookingSystem {

    private static final int CANCELLATION_DEADLINE_DAYS = 2;
    private static final DateTimeFormatter SHORT_DATE =
            DateTimeFormatter.ofPattern("MMM d", Locale.ENGLISH);

    private static String formatPeriod(LocalDate start, LocalDate end) {
        if (start.getMonth() == end.getMonth() && start.getYear() == end.getYear()) {
            return start.format(SHORT_DATE) + "-" + end.getDayOfMonth();
        }
        return start.format(SHORT_DATE) + " - " + end.format(SHORT_DATE);
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

    // ---------- Room hierarchy ----------

    static abstract class Room {
        private final int number;
        private final List<Reservation> reservations = new ArrayList<>();

        Room(int number) {
            this.number = number;
        }

        abstract String getCategory();

        abstract double calculatePrice(int nights);

        String getLabel() {
            return getCategory() + " " + number;
        }

        // Common availability logic - identical for every room category
        boolean isAvailable(LocalDate start, LocalDate end) {
            for (Reservation r : reservations) {
                if (r.isActive() && r.overlaps(start, end)) {
                    return false;
                }
            }
            return true;
        }

        void addReservation(Reservation reservation) {
            reservations.add(reservation);
        }
    }

    static class StandardRoom extends Room {
        StandardRoom(int number) {
            super(number);
        }

        @Override
        String getCategory() {
            return "Standard Room";
        }

        @Override
        double calculatePrice(int nights) {
            return nights * 100.0;
        }
    }

    static class DeluxeRoom extends Room {
        DeluxeRoom(int number) {
            super(number);
        }

        @Override
        String getCategory() {
            return "Deluxe Room";
        }

        @Override
        double calculatePrice(int nights) {
            return nights * 180.0;
        }
    }

    static class Suite extends Room {
        Suite(int number) {
            super(number);
        }

        @Override
        String getCategory() {
            return "Suite";
        }

        @Override
        double calculatePrice(int nights) {
            return nights * 300.0 + 50.0; // per-night rate + flat amenities fee
        }
    }

    // ---------- Reservation ----------

    static class Reservation {
        private final Customer customer;
        private final Room room;
        private final LocalDate start; // check-in
        private final LocalDate end;   // check-out (the night of 'end' is not stayed)
        private boolean active = true;

        Reservation(Customer customer, Room room, LocalDate start, LocalDate end) {
            this.customer = customer;
            this.room = room;
            this.start = start;
            this.end = end;
        }

        boolean isActive() {
            return active;
        }

        boolean overlaps(LocalDate otherStart, LocalDate otherEnd) {
            return start.isBefore(otherEnd) && otherStart.isBefore(end);
        }

        int getNights() {
            return (int) ChronoUnit.DAYS.between(start, end);
        }

        double getPrice() {
            return room.calculatePrice(getNights()); // polymorphic call
        }

        LocalDate getCancellationDeadline() {
            return start.minusDays(CANCELLATION_DEADLINE_DAYS);
        }

        String describe() {
            return customer.getName() + ", " + room.getLabel()
                    + " (" + formatPeriod(start, end) + ")";
        }

        boolean cancel(LocalDate today) {
            if (!active) {
                System.out.println("Reservation for " + describe() + " is already cancelled.");
                return false;
            }
            if (today.isAfter(getCancellationDeadline())) {
                System.out.println("Cannot cancel reservation for " + describe()
                        + ": the cancellation deadline has passed.");
                return false;
            }
            active = false;
            System.out.println("Reservation for " + describe() + " cancelled successfully.");
            return true;
        }
    }

    // ---------- Booking workflow ----------

    static class BookingService {

        void checkAvailability(Room room, LocalDate start, LocalDate end) {
            String period = start.format(SHORT_DATE) + " to " + end.format(SHORT_DATE);
            if (room.isAvailable(start, end)) {
                System.out.println(room.getLabel() + " is available from " + period + ".");
            } else {
                System.out.println(room.getLabel() + " is not available from " + period + ".");
            }
        }

        Reservation reserve(Customer customer, Room room, LocalDate start, LocalDate end) {
            if (!end.isAfter(start)) {
                System.out.println("Check-out must be after check-in.");
                return null;
            }
            if (!room.isAvailable(start, end)) {
                System.out.println(room.getLabel() + " is not available from "
                        + start.format(SHORT_DATE) + " to " + end.format(SHORT_DATE) + ".");
                return null;
            }

            Reservation reservation = new Reservation(customer, room, start, end);
            room.addReservation(reservation);
            System.out.println("Reservation confirmed for " + reservation.describe()
                    + ". Price: $" + String.format("%.2f", reservation.getPrice()) + ".");
            return reservation;
        }
    }

    public static void main(String[] args) {
        BookingService service = new BookingService();

        Customer customerA = new Customer("Customer A");
        Customer customerB = new Customer("Customer B");
        Customer customerC = new Customer("Customer C");

        Room standard101 = new StandardRoom(101);
        Room deluxe201 = new DeluxeRoom(201);

        // "Today" for the cancellation rule (well before the Jan 1 check-in)
        LocalDate today = LocalDate.of(2026, 12, 20);

        LocalDate jan1 = LocalDate.of(2027, 1, 1);
        LocalDate jan5 = LocalDate.of(2027, 1, 5);
        LocalDate jan3 = LocalDate.of(2027, 1, 3);
        LocalDate jan7 = LocalDate.of(2027, 1, 7);

        service.checkAvailability(standard101, jan1, jan5);
        Reservation reservationA = service.reserve(customerA, standard101, jan1, jan5);
        service.reserve(customerB, standard101, jan3, jan7); // rejected - overlaps
        reservationA.cancel(today);                          // before the deadline

        service.reserve(customerC, deluxe201,
                LocalDate.of(2027, 2, 10), LocalDate.of(2027, 2, 12));
    }
}