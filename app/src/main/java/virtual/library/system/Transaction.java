package virtual.library.system;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.opencsv.exceptions.CsvValidationException;

public class Transaction {
    private static final Scanner input = new Scanner(System.in);
    private static final Map<String, String> transactionLog = new HashMap<>();

    private static boolean isValidISBN(String isbn) {
        return isbn.matches("[0-9-]+") && isbn.replaceAll("-", "").length() == 13;
    }

    public static void main(String[] args) {
        Library library = initializeLibrary();

        List<Book> bookList = library.getListOfBooks();
        library.displayAllBooks(bookList);

        String criteria = getUserInput("Enter Book's Title/Author/ISBN:");
        List<Book> searchedBooks = library.searchBooks(criteria);
        library.displayAllBooks(searchedBooks);

        int bookIndex = getBookIndexFromUser(searchedBooks.size());
        if (bookIndex >= 0 && bookIndex < searchedBooks.size()) {
            Book selectedBook = searchedBooks.get(bookIndex);
            library.displaySelectedBook(selectedBook);
        } else {
            System.out.println("Invalid selection. Please enter a valid book number.");
        }

        String isbn = getUserInput("Please enter the ISBN of the book:");
        if (!isValidISBN(isbn)) {
            System.out.println("Invalid ISBN format. Please enter a valid ISBN.");
            return;
        }

        List<Book> bookByISBN = library.searchBooks(isbn);
        library.displayAllBooks(bookByISBN);

        if (confirmAction("Confirm the Title of the book (y/n):")) {
            String userId = getUserInput("Enter your User ID:");
            if (confirmAction("Proceed (y/n)?")) {
                recordTransaction(userId, isbn);
                System.out.println("Book issued...");
            } else {
                System.out.println("Canceled transaction");
                input.close();
                return;
            }
        } else {
            System.out.println("Invalid option.");
        }

        input.close();
    }

    private static Library initializeLibrary() {
        try {
            return new Library();
        } catch (CsvValidationException e) {
            input.close();
            throw new IllegalStateException("Failed to load library due to CSV formatting error.");
        }
    }

    private static String getUserInput(String prompt) {
        System.out.println(prompt);
        return input.nextLine();
    }

    private static int getBookIndexFromUser(int maxIndex) {
        System.out.print("Enter the book number for more details:");
        try {
            return Integer.parseInt(input.nextLine()) - 1;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static boolean confirmAction(String prompt) {
        System.out.print(prompt);
        String response = input.nextLine();
        return response.equalsIgnoreCase("y");
    }

    private static void recordTransaction(String userId, String isbn) {
        Date borrowingDate = new Date();
        transactionLog.put(userId, "ISBN: " + isbn + ", Borrowing Date: " + borrowingDate);
    }

    public static void viewTransactionLog() {
        System.out.println("Transaction Log:");
        for (Map.Entry<String, String> entry : transactionLog.entrySet()) {
            System.out.println("User ID: " + entry.getKey() + ", Transaction: " + entry.getValue());
        }
    }
}
