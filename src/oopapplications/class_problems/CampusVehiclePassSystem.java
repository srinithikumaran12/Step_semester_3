package oopapplications.class_problems;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;


public class CampusVehiclePassSystem {

    // ---------- Chargeable capability ----------

    interface Chargeable {
        String bookChargingBay();
    }

    // ---------- Vehicle hierarchy ----------

    static abstract class Vehicle {
        private final String passNumber;
        private final String ownerName;

        Vehicle(String passNumber, String ownerName) {
            this.passNumber = passNumber;
            this.ownerName = ownerName;
        }

        abstract String getTypeName();

        abstract int getPassFee();

        String getPassNumber() {
            return passNumber;
        }

        String getOwnerName() {
            return ownerName;
        }
    }

    static class Bike extends Vehicle {
        Bike(String passNumber, String ownerName) {
            super(passNumber, ownerName);
        }

        @Override
        String getTypeName() {
            return "Bike";
        }

        @Override
        int getPassFee() {
            return 300;
        }
    }

    static class Car extends Vehicle {
        Car(String passNumber, String ownerName) {
            super(passNumber, ownerName);
        }

        @Override
        String getTypeName() {
            return "Car";
        }

        @Override
        int getPassFee() {
            return 1000;
        }
    }

    static class EBike extends Vehicle implements Chargeable {
        EBike(String passNumber, String ownerName) {
            super(passNumber, ownerName);
        }

        @Override
        String getTypeName() {
            return "EBike";
        }

        @Override
        int getPassFee() {
            return 300;
        }

        @Override
        public String bookChargingBay() {
            return "charging bay allotted";
        }
    }

    static class ECar extends Vehicle implements Chargeable {
        ECar(String passNumber, String ownerName) {
            super(passNumber, ownerName);
        }

        @Override
        String getTypeName() {
            return "ECar";
        }

        @Override
        int getPassFee() {
            return 1000;
        }

        @Override
        public String bookChargingBay() {
            return "charging bay allotted";
        }
    }

    // ---------- Type table: input name -> constructor ----------
    // Input names are matched ignoring case and hyphens, so "E-Car", "ECar" and "ecar" all work.

    private static final Map<String, BiFunction<String, String, Vehicle>> VEHICLE_TYPES = new HashMap<>();

    static {
        VEHICLE_TYPES.put("bike", Bike::new);
        VEHICLE_TYPES.put("car", Car::new);
        VEHICLE_TYPES.put("ebike", EBike::new);
        VEHICLE_TYPES.put("ecar", ECar::new);
    }

    // ---------- Processing ----------

    static List<String> process(String[] lines) {
        Map<String, Vehicle> vehicles = new HashMap<>();
        List<String> output = new ArrayList<>();

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            String[] parts = trimmed.split("\\s+");
            String command = parts[0];

            if (command.equalsIgnoreCase("PASS") && parts.length >= 4) {
                output.add(registerPass(vehicles, parts));
            } else if (command.equalsIgnoreCase("CHARGE") && parts.length == 2) {
                output.add(bookCharging(vehicles, parts[1]));
            } else {
                output.add("Invalid command: " + trimmed);
            }
        }
        return output;
    }

    private static String registerPass(Map<String, Vehicle> vehicles, String[] parts) {
        String typeKey = parts[1].toLowerCase().replace("-", "");
        String passNumber = parts[2];
        String owner = String.join(" ", java.util.Arrays.copyOfRange(parts, 3, parts.length));

        BiFunction<String, String, Vehicle> constructor = VEHICLE_TYPES.get(typeKey);
        if (constructor == null) {
            return "Unknown vehicle type: " + parts[1];
        }
        if (vehicles.containsKey(passNumber)) {
            return passNumber + " rejected: pass already registered";
        }

        Vehicle vehicle = constructor.apply(passNumber, owner);
        vehicles.put(passNumber, vehicle);
        return passNumber + " (" + vehicle.getTypeName() + ") pass fee " + vehicle.getPassFee();
    }

    private static String bookCharging(Map<String, Vehicle> vehicles, String passNumber) {
        Vehicle vehicle = vehicles.get(passNumber);
        if (vehicle == null) {
            return passNumber + " rejected: pass not found";
        }
        if (vehicle instanceof Chargeable) {
            Chargeable chargeable = (Chargeable) vehicle; // safe: guarded by instanceof
            return passNumber + " " + chargeable.bookChargingBay();
        }
        return passNumber + " rejected: charging unsupported";
    }

    public static void main(String[] args) {
        String[] input = {
                "PASS Bike KA01 Asha",
                "PASS ECar KA02 Ravi",
                "CHARGE KA02",
                "CHARGE KA01"
        };

        for (String line : process(input)) {
            System.out.println(line);
        }
        // KA01 (Bike) pass fee 300
        // KA02 (ECar) pass fee 1000
        // KA02 charging bay allotted
        // KA01 rejected: charging unsupported
    }
}