package virtual.library.system;
import java.time.LocalDate;
import java.util.*;
public class ReturnedBooksLog {
    private String userId;
    private String isbn;
    LocalDate returnedDate;
    List<ReturnedBooksLog> returnedBooksLog ;
    public ReturnedBooksLog(String userId, String isbn) {
        this.userId = userId;
        this.isbn = isbn;
        this.returnedDate =  LocalDate.now();
        returnedBooksLog = new ArrayList<>(); 
    }
    public void addReturnedBooksLog(ReturnedBooksLog booksLog){
        returnedBooksLog.add(booksLog);
    }
    public List<ReturnedBooksLog> getReturnedBooksLogs(){
        return returnedBooksLog;
    }
    
}
