package datastructures.class_problems;

import java.util.Arrays;
import java.util.List;


public class LibraryCatalogLookup {

    static class Book {
        private final String isbn;
        private final String title;

        Book(String isbn, String title) {
            this.isbn = isbn;
            this.title = title;
        }

        String getIsbn() {
            return isbn;
        }

        String getTitle() {
            return title;
        }
    }

    static String findBook(List<Book> catalog, String targetIsbn) {
        if (catalog == null || targetIsbn == null) {
            return "Not Found";
        }

        int low = 0;
        int high = catalog.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2; // avoids int overflow of (low + high)
            int comparison = catalog.get(mid).getIsbn().compareTo(targetIsbn);

            if (comparison == 0) {
                return catalog.get(mid).getTitle();
            } else if (comparison < 0) {
                low = mid + 1;  // target is in the right half
            } else {
                high = mid - 1; // target is in the left half
            }
        }

        return "Not Found";
    }

    public static void main(String[] args) {
        List<Book> catalog = Arrays.asList(
                new Book("0001112223", "Introduction to Algebra"),
                new Book("0002223334", "Beginning Python"),
                new Book("0003334445", "Classic Mythology"),
                new Book("0004445556", "Data and Society"),
                new Book("0005556667", "European History"));

        System.out.println(findBook(catalog, "0003334445")); // Classic Mythology
        System.out.println(findBook(catalog, "0009998887")); // Not Found
    }
}