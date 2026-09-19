package inheritance.class_problems;

/**
 * HonorsStudentMember extends StudentMember - multilevel inheritance,
 * three classes deep: LibraryMember -> StudentMember -> HonorsStudentMember.
 */
public class HonorsStudentMember extends StudentMember {

    private final int bonusLimit;

    public HonorsStudentMember(String memberId, int borrowLimit, String course, int bonusLimit) {
        super(memberId, borrowLimit, course);
        this.bonusLimit = bonusLimit;
    }

    int getBonusLimit() {
        return bonusLimit;
    }

    @Override
    String displayInfo() {
        return "Honors Student Member | Course: " + getCourse()
                + " | Bonus Limit: " + bonusLimit
                + " | Books Borrowed: " + getBooksBorrowed();
    }
}