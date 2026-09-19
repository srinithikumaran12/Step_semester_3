package accessmodifiers.assigment_problems;

/**
 * PROBLEM 5: Immutable Loan Receipt & Nightly Circulation Ledger (part 2)
 * A reference-only variant of LoanReceipt, for books that never leave the
 * building - adds a roomNumber field. Also immutable: roomNumber is final.
 */
public class ReferenceOnlyLoanReceipt extends LoanReceipt {

    private final String roomNumber;

    public ReferenceOnlyLoanReceipt(String memberId, String[] bookIds, String roomNumber) {
        super(memberId, bookIds);
        this.roomNumber = roomNumber;
    }

    public String getRoomNumber() {
        return roomNumber;
    }
}