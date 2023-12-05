package virtual.library.system;

import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Transaction {
    private static final Scanner input = new Scanner(System.in);
    private static final String TRANSACTION_FILE_PATH = "app/src/main/resources/Transactions.csv";

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
                decrementNumberOfCopies(library, isbn);
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

    private static void decrementNumberOfCopies(Library library, String isbn) {
        try {
            Optional<Book> optionalBook = findBookByISBN(library, isbn);

            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();

                if (!book.getOutOfStocksStatus()) {
                    book.decrementCountOfBooks();
                    updateBookInLibrary(library, book);
                } else {
                    book.setInStock(false);
                    System.out.println("Out of stock");
                }
            } else {
                System.out.println("Book with ISBN " + isbn + " not found.");
            }
        } catch (Exception e) {
            System.out.println("Error decrementing number of copies: " + e.getMessage());
        }
    }

    private static Optional<Book> findBookByISBN(Library library, String isbn) {
        return library.getListOfBooks().stream()
                .filter(book -> book.getIsbn().equalsIgnoreCase(isbn))
                .findFirst();
    }

    private static void updateBookInLibrary(Library library, Book book) {
        List<Book> books = library.getListOfBooks();
        int index = books.indexOf(book);
        if (index != -1) {
            books.set(index, book);
        }
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
        saveTransaction(transaction);
    }

    private static void saveTransaction(TransactionRecord transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTION_FILE_PATH, true))) {
            writer.write(String.format("%s,%s,%s%n",
                    transaction.getUserId(), transaction.getIsbn(),
                    transaction.getBorrowingDate().format(DateTimeFormatter.ISO_LOCAL_DATE)));
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }
    }

    public static void viewTransactionsForAdmin() {
        try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTION_FILE_PATH))) {
            String line;
            System.out.println("Transaction Log:");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String userId = parts[0];
                String isbn = parts[1];
                LocalDate borrowingDate = LocalDate.parse(parts[2], DateTimeFormatter.ISO_LOCAL_DATE);

                System.out.printf("User ID: %s, ISBN: %s, Borrowing Date: %s%n", userId, isbn, borrowingDate);
            }
        } catch (IOException e) {
            System.out.println("Error reading transactions: " + e.getMessage());
        }
    }
}
