package constructor.class_problems;

/**
 * M1: Library Book Cataloguing
 * Scenario: A librarian receives a batch of book entries in one sitting -
 * some already have a confirmed ISBN; some don't yet. A book with no
 * confirmed ISBN must default to "PENDING", never left blank or null.
 */
public class LibraryBook {

    String title;
    String isbn;

    public LibraryBook(String title, String isbn) {
        this.title = title;
        this.isbn = (isbn == null || isbn.isEmpty()) ? "PENDING" : isbn;
    }

    // Chains to the two-argument constructor for books with no ISBN yet
    public LibraryBook(String title) {
        this(title, "PENDING");
    }

    void printStatus() {
        System.out.println(title + " | " + isbn + " | Catalogued: true");
    }

    public static void main(String[] args) {
        String[] titles = {"Clean Code", "Untitled Draft", "1984", "Notes"};
        String[] isbns = {"978-0132350884", "", "9780451524935", ""};

        for (int i = 0; i < titles.length; i++) {
            LibraryBook book = isbns[i].isEmpty()
                    ? new LibraryBook(titles[i])
                    : new LibraryBook(titles[i], isbns[i]);
            book.printStatus();
        }
        // Clean Code | 978-0132350884 | Catalogued: true
        // Untitled Draft | PENDING | Catalogued: true
        // 1984 | 9780451524935 | Catalogued: true
        // Notes | PENDING | Catalogued: true
    }
}