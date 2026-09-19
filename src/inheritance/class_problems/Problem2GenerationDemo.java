package inheritance.class_problems;

/**
 * PROBLEM 2: Three Branches of the Membership Tree
 */
public class Problem2GenerationDemo {

    public static void main(String[] args) {
        LibraryMember general = new LibraryMember("STU1", 3);
        StudentMember studentMember = new StudentMember("STU2", 3, "CSE");
        HonorsStudentMember honorsMember = new HonorsStudentMember("STU3", 3, "ECE", 2);
        FacultyMember facultyMember = new FacultyMember("STU4", 5, "Physics");

        System.out.println(general.displayInfo());
        // General Member | Books Borrowed: 0
        System.out.println(studentMember.displayInfo());
        // Student Member | Course: CSE | Books Borrowed: 0
        System.out.println(honorsMember.displayInfo());
        // Honors Student Member | Course: ECE | Bonus Limit: 2 | Books Borrowed: 0
        System.out.println(facultyMember.displayInfo());
        // Faculty Member | Department: Physics | Books Borrowed: 0

        System.out.println(LibraryMember.classifyGeneration(honorsMember));
        // Multilevel descendant (3 generations deep)
        System.out.println(LibraryMember.classifyGeneration(facultyMember));
        // Hierarchical sibling (independent branch)

        studentMember.borrowBook();
        studentMember.borrowBook(); // 2 books
        honorsMember.borrowBook();  // 1 book
        facultyMember.borrowBook();
        facultyMember.borrowBook();
        facultyMember.borrowBook(); // 3 books

        int total = LibraryMember.getTotalBooksBorrowed(
                new LibraryMember[]{studentMember, honorsMember, facultyMember});
        System.out.println(total); // 6
    }
}