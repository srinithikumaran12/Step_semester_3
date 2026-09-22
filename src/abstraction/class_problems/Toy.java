package abstraction.class_problems;

/**
 * PROBLEM 1: The Talking Toy Box
 * Every toy makes its own sound - there's no such thing as a generic toy
 * with no specific sound of its own, so Toy is abstract and new Toy()
 * cannot compile.
 */
public abstract class Toy {

    private static int toyIdCounter = 1000;

    private final String toyId;
    protected final String name;

    public Toy(String name) {
        this.name = name;
        toyIdCounter++;
        this.toyId = "TOY-" + toyIdCounter;
    }

    public abstract String makeSound();

    String getToyId() {
        return toyId;
    }

    public static void main(String[] args) {
        ToyCar c = new ToyCar("Speedster");
        System.out.println(c.makeSound()); // Speedster: Vroom vroom!

        ToyRobot r = new ToyRobot("Bolt");
        System.out.println(r.makeSound()); // Bolt: Beep boop!

        System.out.println(c.getToyId()); // TOY-1001
        System.out.println(r.getToyId()); // TOY-1002

        // new Toy("x"); // would not compile - Toy is abstract
    }
}

/**
 * ToyCar - a concrete Toy with its own sound.
 */
class ToyCar extends Toy {

    public ToyCar(String name) {
        super(name);
    }

    @Override
    public String makeSound() {
        return name + ": Vroom vroom!";
    }
}

/**
 * ToyRobot - a concrete Toy with its own sound.
 */
class ToyRobot extends Toy {

    public ToyRobot(String name) {
        super(name);
    }

    @Override
    public String makeSound() {
        return name + ": Beep boop!";
    }
}