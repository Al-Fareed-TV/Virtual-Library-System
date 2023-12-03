package virtual.library.system;

import java.util.List;
import java.util.Scanner;

import com.opencsv.exceptions.CsvValidationException;

public class Transaction {
    private static boolean isValidISBN(String isbn) {
        return isbn.matches("[0-9-]+") && isbn.replaceAll("-", "").length() == 13;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Library library = null;
        try {
            library = new Library();
        } catch (CsvValidationException e) {
            input.close();
            throw new IllegalStateException("Failed to load library due to CSV formatting error.");
        }
        List<Book> bookList = library.getListOfBooks();

        library.displayAllBooks(bookList);
        System.out.println("Enter Book's Title/Author/ISBN :");
        String criteria = input.nextLine();
        List<Book> searchedBooks = library.searchBooks(criteria);
        library.displayAllBooks(searchedBooks);
        System.out.print("Enter the book number for more details: ");
        int bookIndex = input.nextInt() - 1;
        if (bookIndex >= 0 && bookIndex < searchedBooks.size()) {
            Book selectedBook = searchedBooks.get(bookIndex);
            library.displaySelectedBook(selectedBook);
        } else {
            System.out.println("Invalid selection. Please enter a valid book number.");
        }

        System.out.println("Please enter the ISBN of the book : ");
        String isbn = input.nextLine();
        if (!isValidISBN(isbn)) {
            System.out.println("Invalid ISBN format. Please enter a valid ISBN.");
            return;
        }
        List<Book> bookByISBN = library.searchBooks(isbn);
        library.displayAllBooks(bookByISBN);
        System.out.println("Confirm the Title of the book (y/n): ");
        String confirmTitle = input.next();
        if (confirmTitle.equalsIgnoreCase("y")) {
            System.out.println("Proceed(y/n)? : ");
            char proceed = input.next().charAt(0);
            if (proceed == 'y') {
                System.out.println("Book issued...");
            } else {
                System.out.println("Canceled transaction");
                input.close();
                return;
            }
        } else if (!confirmTitle.equalsIgnoreCase("n")) {
            System.out.println("Invalid option..");
        }
        input.close();
    }
}
