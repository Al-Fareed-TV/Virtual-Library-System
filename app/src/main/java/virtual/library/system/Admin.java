package virtual.library.system;

import java.util.*;

public class Admin {
    Scanner input = new Scanner(System.in);

    public void adminOperation() {
        if (verifyAdmin()) {
            proceedWithOperations();
        } else {
            System.out.println("Invalid Credentials...!");
            String retryMessage = "Retry?(y/n)";
            if (confirmUser(retryMessage)) {
                adminOperation();
            } else {
                return;
            }
        }
    }

    public static boolean verifyAdmin() {
        return true;
    }

    public boolean confirmUser(String prompt) {
        System.out.println(prompt);
        return input.nextLine().equalsIgnoreCase("y");
    }

    public String getUserInput(String message) {
        System.out.println(message);
        return input.nextLine();
    }

    public void proceedWithOperations() {
        System.out.println("Operations:\n1.Add Books\n2.Delete Books\n3.View Borrowed Books\n4.Returned Books\n0.Exit");
        String optionsToSelectMessage = "Choose an option from above :";
        int adminSelectedOption = Integer.parseInt(getUserInput(optionsToSelectMessage));
        switch (adminSelectedOption) {
            case 0:
                return;
            case 1:
                addBooks();
                break;
            case 2:
                deleteBooks();
                break;
            case 3:
                viewBorrowedBooks();
                break;
            case 4:
                viewReturnedBooksLog();
                break;
            default: System.out.println("Invalid options selected..!Choose option from the above");
                break;
        }
    }

    public void addBooks() {

    }

    public void deleteBooks() {

    }

    public void viewBorrowedBooks() {
        BorrowedBooks borrowedBooks = new BorrowedBooks();
        List<BorrowedBooks> listOfBorrowedBooks = borrowedBooks.getListOfBorrowedBooks();
        System.out.printf("%-10s %-10s %-10s", "USER ID", "ISBN", "RETURNED DATE");
        for (BorrowedBooks books : listOfBorrowedBooks) {
            System.out.printf("%-10s %-10s %-10s\n", books.getUserId(), books.getIsbn(), books.getIsReturned()?"Returned" : "Not Returned");
        }
    }

    private static void viewReturnedBooksLog() {
        ReturnedBooksLog returnedBooksLog = new ReturnedBooksLog();
        List<ReturnedBooksLog> booksReturned = returnedBooksLog.getReturnedBooksLogs();
        System.out.printf("%-10s %-10s %-10s", "USER ID", "ISBN", "RETURNED DATE");
        for (ReturnedBooksLog books : booksReturned) {
            System.out.printf("%-10s %-10s %-10s\n", books.getUserId(), books.getIsbn(), books.getReturnedDate());
        }
    }
}