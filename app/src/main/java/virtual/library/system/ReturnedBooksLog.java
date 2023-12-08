package virtual.library.system;
import java.time.LocalDate;
import java.util.*;
public class ReturnedBooksLog {
    private int userId;
    private String isbn;
    private String title;
    LocalDate returnedDate;
    List<ReturnedBooksLog> returnedBooksLog ;
    public ReturnedBooksLog(int userId, String isbn,String title) {
        returnedBooksLog = new ArrayList<>(); 
        this.userId = userId;
        this.isbn = isbn;
        this.title = title;
        this.returnedDate =  LocalDate.now();
    }
    public void addReturnedBooksLog(ReturnedBooksLog booksLog){
        returnedBooksLog.add(booksLog);
    }
    public List<ReturnedBooksLog> getReturnedBooksLogs(){
        return returnedBooksLog;
    }
    
}
