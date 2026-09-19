package accessmodifiers.class_problems;

/**
 * PROBLEM 2: Subclass Ticket Access
 * PremiumMovieTicket extends MovieTicket and can read the protected
 * ticketPrice through ITS OWN type - that's the
 * SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE case, which is allowed.
 * Reading it through a plain MovieTicket-typed reference instead would be
 * the PARENT_TYPE case, which Java denies.
 */
public class PremiumMovieTicket extends MovieTicket {

    private double loungeFee;

    public PremiumMovieTicket(String seatNumber, String screenId, double ticketPrice,
                               String movieTitle, double loungeFee) {
        super(seatNumber, screenId, ticketPrice, movieTitle);
        this.loungeFee = loungeFee;
    }

    double totalPriceWithLounge() {
        // Accessing protected ticketPrice through "this" (own type) - ALLOWED
        return this.ticketPrice + loungeFee;
    }

    public static void main(String[] args) {
        PremiumMovieTicket ticket = new PremiumMovieTicket("A1", "S1", 250.0, "Inception", 100.0);
        System.out.println("Total with lounge: " + ticket.totalPriceWithLounge()); // 350.0
    }
}