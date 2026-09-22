package abstraction.class_problems;

/**
 * PROBLEM 2: Warehouse Label Printer
 * Any item that needs a printed label - packages and invoices alike -
 * supports the same printing action, even though they share nothing else.
 */
public interface Printable {
    String printLabel();
}

/**
 * PackageBox - implements Printable directly, with no relationship to
 * Invoice or any shared parent class.
 */
class PackageBox implements Printable {

    private final String trackingId;

    public PackageBox(String trackingId) {
        this.trackingId = trackingId;
    }

    @Override
    public String printLabel() {
        return "Package label: " + trackingId;
    }
}

/**
 * Invoice - implements Printable directly, with no relationship to
 * PackageBox or any shared parent class.
 */
class Invoice implements Printable {

    private final String invoiceNumber;

    public Invoice(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @Override
    public String printLabel() {
        return "Invoice label: " + invoiceNumber;
    }
}

/**
 * Demo / runner for Problem 2.
 */
class PrintableDemo {

    static void printAll(Printable[] items) {
        for (Printable item : items) {
            System.out.println(item.printLabel());
        }
    }

    public static void main(String[] args) {
        PackageBox p = new PackageBox("TRK-88");
        System.out.println(p.printLabel()); // Package label: TRK-88

        Invoice i = new Invoice("INV-42");
        System.out.println(i.printLabel()); // Invoice label: INV-42

        printAll(new Printable[]{p, i});
        // Package label: TRK-88
        // Invoice label: INV-42
    }
}