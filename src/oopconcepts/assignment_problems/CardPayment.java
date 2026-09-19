package constructor.assigment_problems;

/**
 * A5: instanceof Inside a Loop - Canteen Closing-Time Payment Dispatch
 * (part 2 - subclass)
 * A card payment, which adds a 2% processing fee before charging.
 */
public class CardPayment extends Payment {

    double payWithProcessingFee(double amount) {
        double totalCharged = amount + (amount * 0.02);
        System.out.println("Charged (card, incl. fee): Rs " + totalCharged);
        return totalCharged;
    }
}