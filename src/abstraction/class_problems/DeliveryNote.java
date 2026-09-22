package abstraction.class_problems;

/**
 * PROBLEM 5: Package Drop-Off Log
 * DeliveryNote is abstract with an abstract confirmDelivery(), plus an
 * overloaded confirmDelivery(String signature) - true compile-time
 * polymorphism (overloading), separate from the runtime polymorphism of
 * the abstract method itself.
 */
public abstract class DeliveryNote {

    public abstract String confirmDelivery();

    String confirmDelivery(String signature) {
        return confirmDelivery() + ", signed by " + signature;
    }

    static void logAll(DeliveryNote[] notes) {
        for (DeliveryNote note : notes) {
            System.out.println(note.confirmDelivery()); // polymorphic call
        }
    }

    public static void main(String[] args) {
        ParcelNote p = new ParcelNote("TRK-1");
        System.out.println(p.confirmDelivery()); // Parcel TRK-1 delivered

        System.out.println(p.confirmDelivery("J. Smith"));
        // Parcel TRK-1 delivered, signed by J. Smith

        DeliveryNote ref = p; // upcasting: ParcelNote stored as its parent type
        logAll(new DeliveryNote[]{ref, new LetterNote("TRK-2")});
        // Parcel TRK-1 delivered
        // Letter TRK-2 delivered
    }
}

/**
 * ParcelNote - a concrete DeliveryNote.
 */
class ParcelNote extends DeliveryNote {

    private final String trackingId;

    public ParcelNote(String trackingId) {
        this.trackingId = trackingId;
    }

    @Override
    public String confirmDelivery() {
        return "Parcel " + trackingId + " delivered";
    }
}

/**
 * LetterNote - a concrete DeliveryNote.
 */
class LetterNote extends DeliveryNote {

    private final String trackingId;

    public LetterNote(String trackingId) {
        this.trackingId = trackingId;
    }

    @Override
    public String confirmDelivery() {
        return "Letter " + trackingId + " delivered";
    }
}