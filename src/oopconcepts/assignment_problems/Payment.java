package constructor.assigment_problems;

/**
 * A5: instanceof Inside a Loop - Canteen Closing-Time Payment Dispatch
 * (part 1 - base class)
 * A plain cash payment.
 */
public class Payment {

    double pay(double amount) {
        System.out.println("Paid (cash): Rs " + amount);
        return amount;
    }
}