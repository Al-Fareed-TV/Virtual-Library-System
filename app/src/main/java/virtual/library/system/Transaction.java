package virtual.library.system;

import com.opencsv.exceptions.CsvValidationException;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import com.opencsv.CSVReader;

// This is transaction file

public class Transaction {
    private static final Scanner input = new Scanner(System.in);
    private static final String TRANSACTION_FILE_PATH = "app/src/main/resources/BorrowedBooks.csv";

    private static boolean isValidISBN(String isbn) {
        return isbn.matches("[0-9-]+") && isbn.replaceAll("-", "").length() == 13;
    }

    private static TransactionRecord transaction;
    private static List<Book> bookList;

    public static void main(String[] args) {
        Library library = initializeLibrary();
        menu(library);
        input.close();
    }

    private static void menu(Library library) {
        String option;
        do {
            System.out.println("** Library Menu **");
            System.out.println("1. Search Books");
            System.out.println("2. Borrow a Book");
            System.out.println("3. Return a Book");
            System.out.println("0. Exit");
            option = getUserInput("Select an option:");

            switch (option) {
                case "1":
                    searchBooksFlow(library);
                    break;
                case "2":
                    borrowBookFlow(library);
                    break;
                case "3":
                    returnBookFlow(library);
                    break;

                case "0":
                    System.out.println("Thank you for using the library system.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (!option.equals("0"));

    }

    private static void searchBooksFlow(Library library) {
        bookList = library.getListOfBooks();
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
    }

    private static void borrowBookFlow(Library library) {
        String isbn = getUserInput("Please enter the ISBN of the book:");
        if (!isValidISBN(isbn)) {
            System.out.println("Invalid ISBN format. Please enter a valid ISBN.");
            return;
        }

        List<Book> bookByISBN = library.searchBooks(isbn);
        library.displayAllBooks(bookByISBN);

        if (confirmAction("Confirm the Title of the book (y/n):")) {
            int userId = Integer.parseInt(getUserInput("Enter your User ID:"));
            if (confirmAction("Proceed (y/n)?")) {
                Optional<Book> optionalBook = findBookByISBN(library, isbn);
                if (optionalBook.isPresent()) {
                    Book book = optionalBook.get();
                    if (book.borrowBook()) {
                        recordTransaction(userId, book.getTitle(), isbn);
                        System.out.println("Book issued...");
                    } else {
                        System.out.println(
                                "Sorry, the book you are trying to borrow is currently out of stock. Please try again later or select another book.");
                        handleOutOfStockOptions(library);
                    }
                } else {
                    System.out.println("Book with ISBN " + isbn + " not found.");
                }
            } else {
                System.out.println("Canceled transaction");
            }
        } else {
            System.out.println("Invalid option.");
        }
    }

    private static boolean hasUserBorrowedBook(int userId, String isbn) {
        try (CSVReader reader = new CSVReader(new FileReader(TRANSACTION_FILE_PATH))) {
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                int borrowedUserId = Integer.parseInt(nextRecord[0]);
                String borrowedIsbn = nextRecord[1];
                if (borrowedUserId == userId && borrowedIsbn.equals(isbn)) {
                    return true;
                }
            }
        } catch (IOException | CsvValidationException e) {
            System.out.println("Error reading CSV file: " + e.getMessage());
        }
        return false;
    }

    private static void returnBookFlow(Library library) {
        int userId = Integer.parseInt(getUserInput("Enter your User ID:"));
        String isbnOfReturningBook = getUserInput("Enter the ISBN of the book you wish to return:");

        if (!isValidISBN(isbnOfReturningBook)) {
            System.out.println("Invalid ISBN format. Please enter a valid ISBN.");
            return;
        }

        BorrowedBooks borrowedBooksObject = new BorrowedBooks();

        if (hasUserBorrowedBook(userId, isbnOfReturningBook)) {
            Optional<Book> optionalBook = findBookByISBN(library, isbnOfReturningBook);

            if (optionalBook.isPresent()) {
                Book book = optionalBook.get();
                System.out.printf("Book title : '%s'%n", book.getTitle());
                // confirms the title of the book
                if (confirmAction("Confirm the returing book title (y/n):")) {
                    System.out.printf("Borrowing user: User ID %d%n", userId);
                    // confirms the borrowed user
                    if (confirmAction("Confirm the user with their ID (y/n): ")) {
                        if (confirmAction("Proceed with the returning book (y/n):")) {
                            processBookReturn(library, userId, isbnOfReturningBook, book.getTitle(),
                                    borrowedBooksObject);

                        } else {
                            System.out.println("Canceled return.");
                            menu(library);
                        }
                    } else {
                        System.out.println("Redirecting to Menu...");
                        menu(library);
                    }
                } else {
                    System.out.println("Redirecting to Menu...");
                    menu(library);
                }
            } else {
                System.out.println("Book with ISBN " + isbnOfReturningBook + " not found.");
            }
        } else {
            System.out.println("You have not borrowed the book with ISBN " + isbnOfReturningBook + ".");
        }
    }

    private static void processBookReturn(Library library, int userId, String isbn, String title,
            BorrowedBooks borrowedBooksObject) {
        List<BorrowedBooks> borrowedBooks = borrowedBooksObject.getListOfBorrowedBooks();

        borrowedBooks.stream()
                .filter(borrowedBook -> borrowedBook.getUserId() == userId
                        && borrowedBook.getIsbn().equalsIgnoreCase(isbn)
                        && !borrowedBook.getIsReturned())
                .findFirst()
                .ifPresent(borrowedBook -> {
                    borrowedBook.setIsReturned();
                    saveReturnedBookLog(userId, isbn, title, borrowedBooksObject);
                    System.out.println("Book returned successfully.");
                });
    }

    // saves log of returning book
    private static void saveReturnedBookLog(int userId, String isbn, String title, BorrowedBooks borrowedBooksObject) {
        ReturnedBooksLog returnedBooksLog = new ReturnedBooksLog(userId, isbn);

        // Update the BorrowedBooks object to mark the book as returned
        borrowedBooksObject.getListOfBorrowedBooks()
                .stream()
                .filter(borrowedBook -> borrowedBook.getUserId() == userId
                        && borrowedBook.getIsbn().equalsIgnoreCase(isbn)
                        && borrowedBook.getTitle().equalsIgnoreCase(title))
                .findFirst()
                .ifPresent(BorrowedBooks::setIsReturned);
        returnedBooksLog.addReturnedBooksLog(returnedBooksLog);
    }

    private static void handleOutOfStockOptions(Library library) {
        System.out.println("Book is out of stock.");
        System.out.println("Options:");
        System.out.println("1. Return to Main Menu");
        System.out.println("2. Perform Another Search");

        String option = getUserInput("Select an option:");
        switch (option) {
            case "1":
                break;
            case "2":
                searchBooksFlow(library);
                break;
            default:
                System.out.println("Invalid option. Returning to the main menu.");
                break;
        }
    }

    private static Optional<Book> findBookByISBN(Library library, String isbn) {
        return library.getListOfBooks().stream()
                .filter(book -> book.getIsbn().equalsIgnoreCase(isbn))
                .findFirst();
    }

    private static void recordTransaction(int userId, String title, String isbn) {
        LocalDate borrowingDate = LocalDate.now();
        TransactionRecord transaction = new TransactionRecord(userId, isbn, title, borrowingDate);
        saveTransaction(transaction);
    }

    private static void saveTransaction(TransactionRecord transactionRecord) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTION_FILE_PATH, false))) {
            writer.write(String.format("%s,%s,%s,%s,%s%n",
                    transactionRecord.getUserId(),
                    transactionRecord.getIsbn(),
                    transactionRecord.getTitle(),
                    transactionRecord.getBorrowingDate(),
                    transactionRecord.getIsReturnedStatus()));
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
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

    // this method prints the book that has been returned

}
