package oopdesign.assigment_problems;

import java.util.Locale;

/**
 * Question 4: The FitZone Membership Desk
 *
 * Classes and relationships:
 *   MembershipPlan (abstract) <|-- MonthlyPlan, QuarterlyPlan, AnnualPlan
 *   Member 1 ---- 0..* Membership ---- 1 MembershipPlan
 *
 * Fee rules live in the plans (months + discount); status rules live in
 * Membership, so the two concerns stay separate. A new plan (e.g.
 * Half-Yearly) only needs a new MembershipPlan subclass - the desk's
 * workflow doesn't change. Membership status is private and only changes
 * through freeze(), unfreeze() and expire(), which reject invalid moves.
 */
public class FitZoneMembershipDesk {

    private static final double BASE_RATE_PER_MONTH = 1000.0;
    private static final String RUPEE = "\u20B9";

    private static String money(double amount) {
        return RUPEE + String.format(Locale.ENGLISH, "%,.2f", amount);
    }

    private static String withArticle(String label) {
        char first = Character.toLowerCase(label.charAt(0));
        boolean vowel = first == 'a' || first == 'e' || first == 'i' || first == 'o' || first == 'u';
        return (vowel ? "an " : "a ") + label;
    }

    // ---------- Plans ----------

    static abstract class MembershipPlan {
        abstract String getName();

        abstract int getMonths();

        abstract int getDiscountPercent();

        // Common fee calculation - each plan only supplies months and its discount rule
        double calculateFee() {
            double fullPrice = BASE_RATE_PER_MONTH * getMonths();
            return fullPrice * (100 - getDiscountPercent()) / 100.0;
        }
    }

    static class MonthlyPlan extends MembershipPlan {
        @Override
        String getName() {
            return "Monthly";
        }

        @Override
        int getMonths() {
            return 1;
        }

        @Override
        int getDiscountPercent() {
            return 0;
        }
    }

    static class QuarterlyPlan extends MembershipPlan {
        @Override
        String getName() {
            return "Quarterly";
        }

        @Override
        int getMonths() {
            return 3;
        }

        @Override
        int getDiscountPercent() {
            return 10;
        }
    }

    static class AnnualPlan extends MembershipPlan {
        @Override
        String getName() {
            return "Annual";
        }

        @Override
        int getMonths() {
            return 12;
        }

        @Override
        int getDiscountPercent() {
            return 25;
        }
    }

    // ---------- Member and Membership ----------

    static class Member {
        private final String name;

        Member(String name) {
            this.name = name;
        }

        String getName() {
            return name;
        }
    }

    static class Membership {
        enum Status {
            ACTIVE("Active"),
            FROZEN("Frozen"),
            EXPIRED("Expired");

            private final String label;

            Status(String label) {
                this.label = label;
            }

            String getLabel() {
                return label;
            }
        }

        private final Member member;
        private final MembershipPlan plan;
        private Status status = Status.ACTIVE;

        Membership(Member member, MembershipPlan plan) {
            this.member = member;
            this.plan = plan;
        }

        Member getMember() {
            return member;
        }

        MembershipPlan getPlan() {
            return plan;
        }

        Status getStatus() {
            return status;
        }

        boolean canCheckIn() {
            return status == Status.ACTIVE;
        }

        boolean freeze() {
            if (status != Status.ACTIVE) {
                return false;
            }
            status = Status.FROZEN;
            return true;
        }

        boolean unfreeze() {
            if (status != Status.FROZEN) {
                return false;
            }
            status = Status.ACTIVE;
            return true;
        }

        boolean expire() {
            if (status == Status.EXPIRED) {
                return false;
            }
            status = Status.EXPIRED;
            return true;
        }
    }

    // ---------- Desk workflow ----------

    static class MembershipDesk {

        Membership buy(Member member, MembershipPlan plan) {
            Membership membership = new Membership(member, plan);
            System.out.println(plan.getName() + " membership created for " + member.getName()
                    + ". Fee: " + money(plan.calculateFee()) + ". Status: "
                    + membership.getStatus().getLabel() + ".");
            return membership;
        }

        void checkIn(Membership membership) {
            String name = membership.getMember().getName();
            if (membership.canCheckIn()) {
                System.out.println(name + " checked in successfully.");
            } else {
                System.out.println("Check-in denied: " + name + "'s membership is "
                        + membership.getStatus().getLabel() + ".");
            }
        }

        void freeze(Membership membership) {
            Membership.Status before = membership.getStatus();
            if (membership.freeze()) {
                System.out.println(membership.getMember().getName() + "'s membership frozen. Status: "
                        + membership.getStatus().getLabel() + ".");
            } else {
                System.out.println("Cannot freeze " + withArticle(before.getLabel()) + " membership.");
            }
        }

        void unfreeze(Membership membership) {
            Membership.Status before = membership.getStatus();
            if (membership.unfreeze()) {
                System.out.println(membership.getMember().getName() + "'s membership unfrozen. Status: "
                        + membership.getStatus().getLabel() + ".");
            } else {
                System.out.println("Cannot unfreeze " + withArticle(before.getLabel()) + " membership.");
            }
        }

        void expire(Membership membership) {
            Membership.Status before = membership.getStatus();
            if (membership.expire()) {
                System.out.println(membership.getMember().getName() + "'s membership expired. Status: "
                        + membership.getStatus().getLabel() + ".");
            } else {
                System.out.println("Cannot expire " + withArticle(before.getLabel()) + " membership.");
            }
        }
    }

    public static void main(String[] args) {
        MembershipDesk desk = new MembershipDesk();

        Member asha = new Member("Asha");
        Member ravi = new Member("Ravi");

        Membership ashaMembership = desk.buy(asha, new QuarterlyPlan());
        Membership raviMembership = desk.buy(ravi, new MonthlyPlan());

        desk.checkIn(ashaMembership);
        desk.freeze(ashaMembership);
        desk.checkIn(ashaMembership);   // denied - frozen

        desk.expire(raviMembership);
        desk.freeze(raviMembership);    // rejected - expired
    }
}