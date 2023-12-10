package virtual.library.system;
import java.time.LocalDate;
import java.util.*;
public class ReturnedBooksLog {
    private int userId;
    private String isbn;
    LocalDate returnedDate;
    List<ReturnedBooksLog> returnedBooksLog ;
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
    
    
}
