package inheritance.assigment_problems;

/**
 * EliteMember extends PremiumMember - multilevel inheritance, three
 * classes deep: GymMember -> PremiumMember -> EliteMember.
 */
public class EliteMember extends PremiumMember {

    private final String lockerNumber;

    public EliteMember(String memberId, int monthlyFee, String trainerName, String lockerNumber) {
        super(memberId, monthlyFee, trainerName);
        this.lockerNumber = lockerNumber;
    }

    String getLockerNumber() {
        return lockerNumber;
    }

    @Override
    String displayInfo() {
        return "Elite Member | Trainer: " + getTrainerName()
                + " | Locker: " + lockerNumber
                + " | Sessions: " + getSessionsAttended();
    }
}