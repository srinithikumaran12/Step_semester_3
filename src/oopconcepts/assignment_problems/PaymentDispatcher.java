package constructor.assigment_problems;

/**
 * A5: instanceof Inside a Loop - Canteen Closing-Time Payment Dispatch
 * (part 3 - dispatcher)
 * Uses instanceof to check whether a Payment reference is really a
 * CardPayment, dispatching accordingly, and keeps a running total.
 */
public class PaymentDispatcher {

    double totalCollected = 0;

    void processTransaction(Payment payment, double amount) {
        if (payment instanceof CardPayment) {
            CardPayment cardPayment = (CardPayment) payment; // safe: guarded by instanceof
            totalCollected += cardPayment.payWithProcessingFee(amount);
        } else {
            totalCollected += payment.pay(amount);
        }
    }

    public static void main(String[] args) {
        Payment[] payments = {
                new CardPayment(),
                new Payment(),
                new CardPayment(),
                new Payment(),
                new CardPayment()
        };
        double[] amounts = {100, 50, 200, 75, 120};

        PaymentDispatcher dispatcher = new PaymentDispatcher();

        for (int i = 0; i < payments.length; i++) {
            dispatcher.processTransaction(payments[i], amounts[i]);
        }

        System.out.println("Total Collected: Rs " + dispatcher.totalCollected);
        // Charged (card, incl. fee): Rs 102.0
        // Paid (cash): Rs 50.0
        // Charged (card, incl. fee): Rs 204.0
        // Paid (cash): Rs 75.0
        // Charged (card, incl. fee): Rs 122.4
        // Total Collected: Rs 553.4
    }
}