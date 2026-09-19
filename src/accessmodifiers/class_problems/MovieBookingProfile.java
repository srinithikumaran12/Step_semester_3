package accessmodifiers.class_problems;

/**
 * PROBLEM 4: MovieBookingProfile JavaBean & OTP Property
 * A fully JavaBean-compliant class: public no-arg constructor, a
 * convenience constructor chaining via this(...), getX()/setX() pairs
 * (isConfirmed() for the boolean), and otp as a true write-only property -
 * settable but never retrievable again in any form.
 */
public class MovieBookingProfile {

    private String name;
    private boolean confirmed;
    private String otp;

    public MovieBookingProfile() {
    }

    // Convenience constructor for the common case, chains to the no-arg constructor
    public MovieBookingProfile(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    // Write-only: sets otp, but there is no getOtp() anywhere on this class
    public void setOtp(String otp) {
        this.otp = otp;
    }

    public static void main(String[] args) {
        MovieBookingProfile profile = new MovieBookingProfile("Rahul Dev");
        System.out.println(profile.getName()); // Rahul Dev

        profile.setConfirmed(true);
        System.out.println(profile.isConfirmed()); // true

        profile.setOtp("4471"); // no observable output - it can never be read back
    }
}