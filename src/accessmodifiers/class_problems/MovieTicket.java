package accessmodifiers.class_problems;

/**
 * PROBLEM 1 (continued): MovieTicket with the right access level per field.
 *
 * - seatNumber: private   -> internal detail, only this class should touch it directly
 * - screenId:   default   -> only other classes in this same booking package need it
 * - ticketPrice: protected -> subclasses (like PremiumMovieTicket) need to read/adjust it
 * - movieTitle: public    -> freely readable by any part of the app (UI, receipts, etc.)
 */
public class MovieTicket {

    private String seatNumber;
    String screenId;
    protected double ticketPrice;
    public String movieTitle;

    public MovieTicket(String seatNumber, String screenId, double ticketPrice, String movieTitle) {
        this.seatNumber = seatNumber;
        this.screenId = screenId;
        this.ticketPrice = ticketPrice;
        this.movieTitle = movieTitle;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }
}