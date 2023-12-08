package virtual.library.system;

import java.util.*;
import java.time.LocalDate;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.*;

public class BorrowedBooks {
    private final String TRANSACTION_FILE_PATH = "app/src/main/resources/BorrowedBooks.csv";
    private List<BorrowedBooks> borrowedBooks;
    private int userId;
    private String isbn;
    private String title;
    private LocalDate borrowDate;
    private boolean isReturned;

    public BorrowedBooks() {
        borrowedBooks = new ArrayList<>();
        try {
            getRecordOfListOfBorrowedBooks();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BorrowedBooks(int userId, String isbn, String title, LocalDate borrowDate, boolean isReturned) {
        this.userId = userId;
        this.isbn = isbn;
        this.title = title;
        this.borrowDate = borrowDate;
        this.isReturned = isReturned;
        borrowedBooks = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getIsReturned() {
        return isReturned;
    }

    public void setIsReturned() {
        this.isReturned = !isReturned;
    }

    public void addBorrowedBooks(BorrowedBooks books) {
        borrowedBooks.add(books);
    }

    private List<BorrowedBooks> getRecordOfListOfBorrowedBooks() throws CsvValidationException {
        try (CSVReader reader = new CSVReader(new FileReader(TRANSACTION_FILE_PATH))) {
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                int userId = Integer.parseInt(nextRecord[0]);
                String isbn = nextRecord[1];
                String title = nextRecord[2];
                LocalDate date = LocalDate.parse(nextRecord[3]);
                boolean isReturned = Boolean.parseBoolean(nextRecord[4]);
                BorrowedBooks books = new BorrowedBooks(userId, isbn, title, date, isReturned);
                addBorrowedBooks(books);
            }
        } catch (IOException e) {
            System.out.println("Error in reading csv file Borrowed book ");
        }
        return null;
    }

    public List<BorrowedBooks> getListOfBorrowedBooks() {
        return borrowedBooks;
    }
}
