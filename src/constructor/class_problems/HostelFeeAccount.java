package constructor.class_problems;

/**
 * M5: Account Batch Payments (part 2 - subclass)
 * A hostel fee account, which pays in two installments instead of one.
 */
public class HostelFeeAccount extends FeeAccount {

    public HostelFeeAccount(String accountId) {
        super(accountId);
    }
}