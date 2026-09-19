package string.assigment_problems;

/**
 * Assignment 3: Product Inventory CSV Parser
 * Scenario: The warehouse team receives inventory updates as CSV lines
 * and needs a quick parser to split each line into fields and print a
 * formatted record.
 */
public class ProductInventoryCsvParser {

    void parseInventoryRecord(String csvLine) {
        String[] fields = csvLine.split(",");

        if (fields.length != 3) {
            System.out.println("Invalid Record");
            return;
        }

        String productName = fields[0];
        String sku = fields[1];
        String quantity = fields[2];

        System.out.println("Product: " + productName + " | SKU: " + sku + " | Qty: " + quantity);
    }

    public static void main(String[] args) {
        ProductInventoryCsvParser parser = new ProductInventoryCsvParser();

        parser.parseInventoryRecord("Wireless Mouse,WM-2201,150");
        // Product: Wireless Mouse | SKU: WM-2201 | Qty: 150

        parser.parseInventoryRecord("Wireless Mouse,150");
        // Invalid Record
    }
}