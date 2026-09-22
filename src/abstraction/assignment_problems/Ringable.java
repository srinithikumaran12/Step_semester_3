package abstraction.assigment_problems;

/**
 * PROBLEM 1: Morning Wake-Up Circuit
 * Any device that can "ring" plugs into one shared ringing system, even
 * though AlarmClock and Doorbell have nothing else in common.
 */
public interface Ringable {
    String ring();
}

/**
 * AlarmClock - implements Ringable directly, no shared parent class.
 */
class AlarmClock implements Ringable {

    private final String time;

    public AlarmClock(String time) {
        this.time = time;
    }

    @Override
    public String ring() {
        return "Alarm ringing for " + time;
    }
}

/**
 * Doorbell - implements Ringable directly, no shared parent class.
 */
class Doorbell implements Ringable {

    private final String location;

    public Doorbell(String location) {
        this.location = location;
    }

    @Override
    public String ring() {
        return "Doorbell ringing at " + location;
    }
}

/**
 * Demo / runner for Problem 1.
 */
class RingableDemo {

    static void ringAll(Ringable[] devices) {
        for (Ringable device : devices) {
            System.out.println(device.ring());
        }
    }

    public static void main(String[] args) {
        AlarmClock a = new AlarmClock("7:00 AM");
        System.out.println(a.ring()); // Alarm ringing for 7:00 AM

        Doorbell d = new Doorbell("Front Door");
        System.out.println(d.ring()); // Doorbell ringing at Front Door

        ringAll(new Ringable[]{a, d});
        // Alarm ringing for 7:00 AM
        // Doorbell ringing at Front Door
    }
}