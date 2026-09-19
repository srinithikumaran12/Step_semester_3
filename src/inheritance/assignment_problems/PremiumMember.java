package inheritance.assigment_problems;

/**
 * PremiumMember extends GymMember directly (single inheritance).
 * Forwards shared fields via super(...) - does not duplicate memberId or
 * monthlyFee as its own fields.
 */
public class PremiumMember extends GymMember {

    private final String trainerName;

    public PremiumMember(String memberId, int monthlyFee, String trainerName) {
        super(memberId, monthlyFee);
        this.trainerName = trainerName;
    }

    String getTrainerName() {
        return trainerName;
    }

    @Override
    String displayInfo() {
        return "Premium Member | Trainer: " + trainerName + " | Sessions: " + getSessionsAttended();
    }

    // PROBLEM 3: halves every late fee before applying it via the parent's own logic
    @Override
    protected void chargeLateFee(int amount) {
        super.chargeLateFee(amount / 2);
    }
}