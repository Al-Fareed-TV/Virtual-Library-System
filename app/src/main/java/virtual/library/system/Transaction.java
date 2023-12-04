package virtual.library.system;

import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Transaction {
    private static final Scanner input = new Scanner(System.in);
    private static final List<TransactionRecord> transactionLog = new ArrayList<>();

    private static boolean isValidISBN(String isbn) {
        // A more robust ISBN validation can be implemented here
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
        LocalDate borrowingDate = LocalDate.now();
        TransactionRecord transaction = new TransactionRecord(userId, isbn, borrowingDate);
        transactionLog.add(transaction);
        // Additional logic to store the transaction persistently (e.g., in a file or database) can be added here
    }

    public static void viewTransactionLog() {
        System.out.println("Transaction Log:");
        for (TransactionRecord transaction : transactionLog) {
            System.out.println("User ID: " + transaction.getUserId() +
                    ", ISBN: " + transaction.getIsbn() +
                    ", Borrowing Date: " + transaction.getBorrowingDate());
        }
    }
}
