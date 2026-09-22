package abstraction.class_problems;

/**
 * PROBLEM 4: Smart Kitchen Assistant
 * KitchenTool is abstract with an abstract prepare() method, and a private
 * speedLevel exposed only through JavaBean getSpeedLevel()/setSpeedLevel()
 * - setSpeedLevel() rejects anything outside 1-5. Washable is a completely
 * separate concern (cleaning) from how a tool prepares food.
 */
public abstract class KitchenTool {

    private int speedLevel = 1;

    public abstract String prepare();

    int getSpeedLevel() {
        return speedLevel;
    }

    void setSpeedLevel(int speedLevel) {
        if (speedLevel < 1 || speedLevel > 5) {
            return; // reject first, assign second
        }
        this.speedLevel = speedLevel;
    }

    public static void main(String[] args) {
        Blender b = new Blender();
        b.setSpeedLevel(3);
        System.out.println(b.getSpeedLevel()); // 3

        b.setSpeedLevel(9); // rejected, speed level stays 3
        System.out.println(b.getSpeedLevel()); // 3

        System.out.println(b.prepare()); // Blending at speed 3
        System.out.println(b.clean());   // Blender rinsed and dried
    }
}

/**
 * Washable - a completely separate concern from how a tool prepares food.
 */
interface Washable {
    String clean();
}

/**
 * Blender extends KitchenTool (food preparation) and implements Washable
 * (cleaning) - two unrelated concerns combined.
 */
class Blender extends KitchenTool implements Washable {

    @Override
    public String prepare() {
        return "Blending at speed " + getSpeedLevel();
    }

    @Override
    public String clean() {
        return "Blender rinsed and dried";
    }
}