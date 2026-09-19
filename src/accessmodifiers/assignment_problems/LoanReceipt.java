package accessmodifiers.assigment_problems;

/**
 * PROBLEM 5: Immutable Loan Receipt & Nightly Circulation Ledger (part 1)
 * LoanReceipt is genuinely immutable: fields are final, and bookIds is
 * defensively copied both going in (constructor) and going out (getter).
 *
 * Note: the class is intentionally NOT declared final, because
 * ReferenceOnlyLoanReceipt needs to extend it so that
 * processNightlyCirculation() can accept both kinds through a single
 * LoanReceipt[] array and tell them apart with instanceof. Immutability is
 * still fully enforced through private final fields plus defensive
 * copying in the constructor and getter.
 */
public class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String memberId, String[] bookIds) {
        this.memberId = memberId;
        this.bookIds = bookIds.clone(); // defensive copy IN
    }

    public String getMemberId() {
        return memberId;
    }

    public String[] getBookIds() {
        return bookIds.clone(); // defensive copy OUT
    }

    public LoanReceipt withCorrectedBookId(int index, String newId) {
        String[] correctedIds = bookIds.clone();
        correctedIds[index] = newId;
        return new LoanReceipt(memberId, correctedIds);
    }

    public static void main(String[] args) {
        LoanReceipt r = new LoanReceipt("LIB-8841", new String[]{"BK-100", "BK-101"});

        String[] ids = r.getBookIds();
        ids[0] = "HACKED"; // mutating the returned array must not affect the receipt
        System.out.println(r.getBookIds()[0]); // BK-100

        LoanReceipt corrected = r.withCorrectedBookId(1, "BK-102");
        System.out.println(java.util.Arrays.toString(r.getBookIds()));         // [BK-100, BK-101]
        System.out.println(java.util.Arrays.toString(corrected.getBookIds())); // [BK-100, BK-102]
    }
}