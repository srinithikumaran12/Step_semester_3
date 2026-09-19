package accessmodifiers.class_problems;

/**
 * PROBLEM 1: Movie Ticket Field Visibility Checker
 * PROBLEM 2: Subclass Ticket Access
 *
 * Classifies whether a field access attempt is ALLOWED or DENIED based on
 * Java's real access-modifier rules, across 5 possible accessor contexts:
 * SAME_CLASS, SAME_PACKAGE, DIFFERENT_PACKAGE,
 * SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE, SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE.
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
                // DIFFERENT_PACKAGE and SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE are both denied:
                // protected cross-package access only works through the subclass's own type,
                // not through a reference declared as the parent type.
                return "DENIED";

            case "public":
                return "ALLOWED";

            default:
                return "DENIED";
        }
    }

    static String summarizeBatch(String[][] attempts) {
        int allowed = 0;
        int denied = 0;

        for (String[] attempt : attempts) {
            String result = classifyAccess(attempt[0], attempt[1]);
            if (result.equals("ALLOWED")) {
                allowed++;
            } else {
                denied++;
            }
        }

        return "Allowed: " + allowed + " | Denied: " + denied;
    }

    public static void main(String[] args) {
        System.out.println(classifyAccess("private", "SAME_CLASS")); // ALLOWED
        System.out.println(classifyAccess("protected", "DIFFERENT_PACKAGE")); // DENIED

        String[][] batch = {
                {"default", "SAME_PACKAGE"},
                {"default", "DIFFERENT_PACKAGE"},
                {"public", "DIFFERENT_PACKAGE"}
        };
        System.out.println(summarizeBatch(batch)); // Allowed: 2 | Denied: 1

        System.out.println(classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE")); // ALLOWED
        System.out.println(classifyAccess("protected", "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE")); // DENIED
    }
}