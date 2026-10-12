package oopdesign.class_problems;

import java.util.ArrayList;
import java.util.List;

/**
 * Question 1: Vehicle Rental System
 *
 * Classes and relationships:
 *   Vehicle (abstract)  <|--  Sedan, SUV, Truck     (generalization)
 *   Customer 1 ---- 0..* Rental                      (a customer can have many rentals)
 *   Vehicle  1 ---- 0..* Rental                      (a vehicle has many rentals over time,
 *                                                     but at most ONE active at a time)
 *
 * Each vehicle category implements its own calculateCharge(). The common
 * rental workflow (RentalService) only talks to the Vehicle abstraction,
 * so a new category (e.g. Van) can be added without touching it.
 */
public class VehicleRentalSystem {

    // ---------- Vehicle hierarchy ----------

    static abstract class Vehicle {
        private final String name;
        private boolean available = true;

        Vehicle(String name) {
            this.name = name;
        }

        abstract double calculateCharge(int days);

        String getName() {
            return name;
        }

        boolean isAvailable() {
            return available;
        }

        void markRented() {
            available = false;
        }

        void markReturned() {
            available = true;
        }
    }

    static class Sedan extends Vehicle {
        Sedan(String name) {
            super(name);
        }

        @Override
        double calculateCharge(int days) {
            return days * 50.0;
        }
    }

    static class SUV extends Vehicle {
        SUV(String name) {
            super(name);
        }

        @Override
        double calculateCharge(int days) {
            return days * 80.0;
        }
    }

    static class Truck extends Vehicle {
        Truck(String name) {
            super(name);
        }

        @Override
        double calculateCharge(int days) {
            return days * 120.0 + 100.0; // per-day rate + flat insurance fee
        }
    }

    // ---------- Customer and Rental ----------

    static class Customer {
        private final String name;

        Customer(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    static class Rental {
        private final Vehicle vehicle;
        private final Customer customer;
        private final int days;
        private boolean active = true;

        Rental(Vehicle vehicle, Customer customer, int days) {
            this.vehicle = vehicle;
            this.customer = customer;
            this.days = days;
        }

        Vehicle getVehicle() {
            return vehicle;
        }

        Customer getCustomer() {
            return customer;
        }

        int getDays() {
            return days;
        }

        boolean isActive() {
            return active;
        }

        void close() {
            active = false;
        }
    }

    // ---------- Common rental workflow (never changes for new vehicle types) ----------

    static class RentalService {
        private final List<Rental> rentals = new ArrayList<>();

        Rental rent(Customer customer, Vehicle vehicle, int days) {
            if (days <= 0) {
                System.out.println("Rental duration must be at least 1 day.");
                return null;
            }
            if (!vehicle.isAvailable()) {
                System.out.println(vehicle.getName() + " is currently unavailable.");
                return null;
            }

            vehicle.markRented();
            Rental rental = new Rental(vehicle, customer, days);
            rentals.add(rental);

            double charge = vehicle.calculateCharge(days); // polymorphic call
            System.out.println(vehicle.getName() + " rented successfully by " + customer.getName()
                    + ". Rental charge: $" + String.format("%.2f", charge));
            return rental;
        }

        void returnVehicle(Rental rental) {
            if (rental == null || !rental.isActive()) {
                System.out.println("No active rental to return.");
                return;
            }
            rental.close();
            rental.getVehicle().markReturned();
            System.out.println(rental.getVehicle().getName() + " returned by "
                    + rental.getCustomer().getName() + ".");
        }
    }

    public static void main(String[] args) {
        RentalService service = new RentalService();

        Customer customer1 = new Customer("Customer 1");
        Customer customer2 = new Customer("Customer 2");
        Customer customer3 = new Customer("Customer 3");

        Vehicle sedanA = new Sedan("Sedan A");
        Vehicle suvB = new SUV("SUV B");

        Rental rental1 = service.rent(customer1, sedanA, 3);
        service.rent(customer2, sedanA, 2); // rejected - already rented
        service.returnVehicle(rental1);
        service.rent(customer3, suvB, 5);
    }
}