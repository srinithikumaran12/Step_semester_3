package abstraction.assigment_problems;

/**
 * PROBLEM 4: Digital Classroom Setup
 * ClassroomDevice is abstract with an abstract operate() method.
 * Chargeable has two overloaded methods - true compile-time polymorphism.
 */
public abstract class ClassroomDevice {

    public abstract String operate();

    public static void main(String[] args) {
        Tablet t = new Tablet("TAB-5");
        System.out.println(t.operate()); // Tablet TAB-5 displaying lesson

        System.out.println(t.charge()); // TAB-5 charging
        System.out.println(t.charge(30)); // TAB-5 charging for 30 minutes
    }
}

/**
 * Chargeable - two overloaded methods sharing a name; Java decides which
 * one runs based on how many arguments are passed in.
 */
interface Chargeable {
    String charge();
    String charge(int minutes);
}

/**
 * Tablet extends ClassroomDevice and implements Chargeable, providing
 * both charge() methods.
 */
class Tablet extends ClassroomDevice implements Chargeable {

    private final String assetTag;

    public Tablet(String assetTag) {
        this.assetTag = assetTag;
    }

    @Override
    public String operate() {
        return "Tablet " + assetTag + " displaying lesson";
    }

    @Override
    public String charge() {
        return assetTag + " charging";
    }

    @Override
    public String charge(int minutes) {
        return assetTag + " charging for " + minutes + " minutes";
    }
}