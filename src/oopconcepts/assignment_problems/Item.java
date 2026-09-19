package constructor.assigment_problems;

/**
 * A2: this Keyword for Canteen Inventory - Batch Restock
 * Scenario: Every Friday evening, the canteen restocks the same fixed
 * quantity of every item on the shelf at once.
 */
public class Item {

    String itemName;
    int stock;

    public Item(String itemName, int stock) {
        this.itemName = itemName;
        this.stock = stock;
    }

    void restock(int stock) {
        this.stock = this.stock + stock;
    }

    public static void main(String[] args) {
        Item[] items = {
                new Item("Samosa", 15),
                new Item("Tea Powder", 40),
                new Item("Bread", 8),
                new Item("Biscuit Packs", 25)
        };

        for (Item item : items) {
            item.restock(20);
            System.out.println(item.itemName + " | Final Stock: " + item.stock);
        }
        // Samosa | Final Stock: 35
        // Tea Powder | Final Stock: 60
        // Bread | Final Stock: 28
        // Biscuit Packs | Final Stock: 45
    }
}