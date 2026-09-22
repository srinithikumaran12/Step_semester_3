package abstraction.assigment_problems;

/**
 * PROBLEM 3: Backyard Toolshed Routine
 * GardenTool is abstract with an abstract use() method - the top of a
 * genuine 3-level hierarchy: GardenTool -> CuttingTool -> Pruner.
 *
 * Note: since GardenTool.use() is abstract (no body), CuttingTool cannot
 * actually call super.use() - there's nothing there yet to call.
 * CuttingTool provides the first real implementation directly, and Pruner
 * then calls super.use() on top of that, matching the sample output.
 */
public abstract class GardenTool {

    public abstract String use();

    public static void main(String[] args) {
        CuttingTool c = new CuttingTool();
        System.out.println(c.use());
        // Using the tool in the garden, blade sharpened first

        Pruner p = new Pruner();
        System.out.println(p.use());
        // Using the tool in the garden, blade sharpened first, then trimming branches precisely
    }
}

/**
 * CuttingTool overrides use() with its own concrete version.
 */
class CuttingTool extends GardenTool {

    @Override
    public String use() {
        return "Using the tool in the garden, blade sharpened first";
    }
}

/**
 * Pruner extends CuttingTool (multilevel hierarchy, 3 classes deep).
 * Calls super.use() first, then adds its own extra detail.
 */
class Pruner extends CuttingTool {

    @Override
    public String use() {
        return super.use() + ", then trimming branches precisely";
    }
}