package constructor.assigment_problems;

/**
 * A1: Overloaded Constructors for Hackathon Registration
 * Scenario: Some participants arrive already part of a team; others
 * register solo and get a placeholder team name.
 */
public class Participant {

    String name;
    String teamName;
    boolean registered;

    public Participant(String name, String teamName) {
        this.name = name;
        this.teamName = teamName;
        this.registered = true;
    }

    // Solo entries - chains to the two-argument constructor with a placeholder team
    public Participant(String name) {
        this(name, "Unassigned");
    }

    void printStatus() {
        System.out.println(name + " | " + teamName + " | Registered: " + registered);
    }

    public static void main(String[] args) {
        String[] names = {"Ravi", "Meera", "Karthik", "Divya"};
        String[] teamNames = {"ByteBusters", "", "CodeCrafters", ""};

        for (int i = 0; i < names.length; i++) {
            Participant participant;
            if (teamNames[i].isEmpty()) {
                participant = new Participant(names[i]);
            } else {
                participant = new Participant(names[i], teamNames[i]);
            }
            participant.printStatus();
        }
        // Ravi | ByteBusters | Registered: true
        // Meera | Unassigned | Registered: true
        // Karthik | CodeCrafters | Registered: true
        // Divya | Unassigned | Registered: true
    }
}