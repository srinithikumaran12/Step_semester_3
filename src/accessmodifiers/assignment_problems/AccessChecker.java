package accessmodifiers.assigment_problems;

/**
 * PROBLEM 1: Membership Field Reach Checker
 * PROBLEM 2: Reference Desk Subclass Reach
 *
 * Classifies field access attempts using Java's real visibility rules
 * across 5 accessor contexts, then offers two different aggregation
 * styles: a per-modifier breakdown (Problem 1) and an early-exit scan
 * for the first denied attempt (Problem 2).
 */
public class AccessChecker {

    static String classifyAccess(String fieldModifier, String accessorContext) {
        switch (fieldModifier) {
            case "private":
                return accessorContext.equals("SAME_CLASS") ? "ALLOWED" : "DENIED";

            case "default":
                return (accessorContext.equals("SAME_CLASS") || accessorContext.equals("SAME_PACKAGE"))
                        ? "ALLOWED" : "DENIED";

            case "protected":
                if (accessorContext.equals("SAME_CLASS")
                        || accessorContext.equals("SAME_PACKAGE")
                        || accessorContext.equals("SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")) {
                    return "ALLOWED";
                }
                return "DENIED";

            case "public":
                return "ALLOWED";

            default:
                return "DENIED";
        }
    }

    // PROBLEM 1: groups results per modifier, not as one flat total.
    // Every modifier is always present in the output, even with zero attempts.
    static String summarizeByModifier(String[][] attempts) {
        String[] modifiers = {"private", "default", "protected", "public"};
        int[] allowedCounts = new int[modifiers.length];
        int[] deniedCounts = new int[modifiers.length];

        for (String[] attempt : attempts) {
            String modifier = attempt[0];
            String context = attempt[1];
            String result = classifyAccess(modifier, context);

            for (int i = 0; i < modifiers.length; i++) {
                if (modifiers[i].equals(modifier)) {
                    if (result.equals("ALLOWED")) {
                        allowedCounts[i]++;
                    } else {
                        deniedCounts[i]++;
                    }
                    break;
                }
            }
        }

        StringBuilder summary = new StringBuilder();
        for (int i = 0; i < modifiers.length; i++) {
            summary.append(modifiers[i]).append(": ")
                    .append(allowedCounts[i]).append(" allowed / ")
                    .append(deniedCounts[i]).append(" denied");
            if (i < modifiers.length - 1) {
                summary.append(" | ");
            }
        }

        return summary.toString();
    }

    // PROBLEM 2: scans strictly in order and stops at the first denial - no
    // building a full result list first.
    static String firstDeniedAttempt(String[][] attempts) {
        for (int i = 0; i < attempts.length; i++) {
            String modifier = attempts[i][0];
            String context = attempts[i][1];

            if (classifyAccess(modifier, context).equals("DENIED")) {
                return modifier + " via " + context + " (attempt #" + (i + 1) + ")";
            }
        }
        return "None Denied";
    }

    public static void main(String[] args) {
        System.out.println(classifyAccess("private", "SAME_CLASS")); // ALLOWED
        System.out.println(classifyAccess("protected", "DIFFERENT_PACKAGE")); // DENIED

        String[][] batch = {
                {"private", "SAME_CLASS"},
                {"private", "SAME_PACKAGE"},
                {"default", "SAME_PACKAGE"},
                {"default", "DIFFERENT_PACKAGE"},
                {"protected", "SAME_PACKAGE"},
                {"protected", "SAME_CLASS"},
                {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println(summarizeByModifier(batch));
        // private: 1 allowed / 1 denied | default: 1 allowed / 1 denied | protected: 2 allowed / 0 denied | public: 1 allowed / 0 denied

        String[][] scan1 = {
                {"public", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"},
                {"protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE"},
                {"protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"}
        };
        System.out.println(firstDeniedAttempt(scan1));
        // protected via SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE (attempt #2)

        String[][] scan2 = {
                {"public", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"},
                {"protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE"}
        };
        System.out.println(firstDeniedAttempt(scan2));
        // None Denied
    }
}