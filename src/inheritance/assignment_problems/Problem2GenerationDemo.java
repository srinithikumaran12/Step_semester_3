package inheritance.assigment_problems;

/**
 * PROBLEM 2: Three Tiers of Gym Membership
 */
public class Problem2GenerationDemo {

    public static void main(String[] args) {
        GymMember standard = new GymMember("MEM1", 1000);
        PremiumMember premiumMember = new PremiumMember("MEM2", 2000, "Coach Riya");
        EliteMember eliteMember = new EliteMember("MEM3", 3000, "Coach Arjun", "L12");
        GroupClassMember groupClassMember = new GroupClassMember("MEM4", 1500, "Zumba");

        System.out.println(standard.displayInfo());
        // Standard Member | Sessions: 0
        System.out.println(premiumMember.displayInfo());
        // Premium Member | Trainer: Coach Riya | Sessions: 0
        System.out.println(eliteMember.displayInfo());
        // Elite Member | Trainer: Coach Arjun | Locker: L12 | Sessions: 0
        System.out.println(groupClassMember.displayInfo());
        // Group Class Member | Class: Zumba | Sessions: 0

        System.out.println(GymMember.classifyGeneration(eliteMember));
        // Multilevel descendant (3 generations deep)
        System.out.println(GymMember.classifyGeneration(groupClassMember));
        // Hierarchical sibling (independent branch)

        premiumMember.attendSession();
        premiumMember.attendSession();
        premiumMember.attendSession(); // 3 sessions
        eliteMember.attendSession();
        eliteMember.attendSession(); // 2 sessions
        groupClassMember.attendSession();
        groupClassMember.attendSession();
        groupClassMember.attendSession();
        groupClassMember.attendSession(); // 4 sessions

        int total = GymMember.getTotalSessionsAttended(
                new GymMember[]{premiumMember, eliteMember, groupClassMember});
        System.out.println(total); // 9
    }
}