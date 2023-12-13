package virtual.library.system;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
public class ReturnedBooksLog {
    private int userId;
    private String isbn;
    LocalDate returnedDate;
    List<ReturnedBooksLog> returnedBooksLog ;
    private static final String RETURNED_BOOKS_FILE_PATH = "app/src/main/resources/ReturnedBooks.csv";

    public ReturnedBooksLog() {
    }
    public ReturnedBooksLog(int userId, String isbn) {
        returnedBooksLog = new ArrayList<>(); 
        this.userId = userId;
        this.isbn = isbn;
        this.returnedDate =  LocalDate.now();
    }
    public void addReturnedBooksLog(ReturnedBooksLog booksLog){
        returnedBooksLog.add(booksLog);
        saveReturnedBooksToCSVFile(returnedBooksLog);
    }
    public List<ReturnedBooksLog> getReturnedBooksLogs(){
        return returnedBooksLog;
    }
    public int getUserId() {
        return userId;
    }
    public String getIsbn() {
        return isbn;
    }
    public LocalDate getReturnedDate() {
        return returnedDate;
    }
     //method to save returned books log in csv file
    private void saveReturnedBooksToCSVFile(List<ReturnedBooksLog> returnedBooksLogs) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RETURNED_BOOKS_FILE_PATH))) {
            for (ReturnedBooksLog books : returnedBooksLogs) {
                writer.write(String.format("%s,%s,%s,%s,%s%n",
                        books.getUserId(),
                        books.getIsbn(),
                        books.getReturnedDate()));
            }
        } catch (IOException e) {
            System.out.println("Error saving borrowed books to file: " + e.getMessage());
        }
    }

    
}
