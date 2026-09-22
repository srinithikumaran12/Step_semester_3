package abstraction.assigment_problems;

/**
 * PROBLEM 5: Skyline Delivery Fleet
 * Drone is abstract with an abstract fly() method. Trackable is a
 * completely separate concern: DeliveryDrone is both a Drone and
 * Trackable; ScoutDrone is a Drone but NOT Trackable (a sibling that
 * opts out of that capability); GroundRobot is Trackable with no
 * relationship to Drone at all.
 */
public abstract class Drone {

    protected final String id;

    public Drone(String id) {
        this.id = id;
    }

    public abstract String fly();

    static String getLocationIfTrackable(Object o) {
        if (o instanceof Trackable) {
            Trackable trackable = (Trackable) o; // safe: guarded by instanceof
            return trackable.getLocation();
        }
        return "Tracking not available";
    }

    public static void main(String[] args) {
        DeliveryDrone d = new DeliveryDrone("DR-1");
        System.out.println(getLocationIfTrackable(d)); // DR-1 at Sector 4

        ScoutDrone s = new ScoutDrone("SC-1");
        System.out.println(getLocationIfTrackable(s)); // Tracking not available

        GroundRobot g = new GroundRobot("GR-1");
        System.out.println(getLocationIfTrackable(g)); // GR-1 at Sector 4
    }
}

/**
 * Trackable - a capability unrelated to the Drone hierarchy; what matters
 * for tracking is implementing this interface, not ancestry.
 */
interface Trackable {
    String getLocation();
}

/**
 * DeliveryDrone extends Drone and implements Trackable.
 */
class DeliveryDrone extends Drone implements Trackable {

    public DeliveryDrone(String id) {
        super(id);
    }

    @Override
    public String fly() {
        return id + " flying delivery route";
    }

    @Override
    public String getLocation() {
        return id + " at Sector 4";
    }
}

/**
 * ScoutDrone extends Drone (a sibling of DeliveryDrone) but deliberately
 * does NOT implement Trackable.
 */
class ScoutDrone extends Drone {

    public ScoutDrone(String id) {
        super(id);
    }

    @Override
    public String fly() {
        return id + " flying scout route";
    }
}

/**
 * GroundRobot implements only Trackable, with no relationship to Drone.
 */
class GroundRobot implements Trackable {

    private final String id;

    public GroundRobot(String id) {
        this.id = id;
    }

    @Override
    public String getLocation() {
        return id + " at Sector 4";
    }
}