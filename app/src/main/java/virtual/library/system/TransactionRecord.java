package virtual.library.system;
import java.time.LocalDate;
public class TransactionRecord {
    private int userId;
    private LocalDate borrowedDate;
    private String isbn;
    private String title;
    public TransactionRecord() {
    }
    public int getUserId() {
        return userId;
    }
    public LocalDate getBorrowingDate() {
        return borrowedDate;
    }
    public String getIsbn() {
        return isbn;
    }
    public String getTitle(){
        return title;
    }
    public TransactionRecord(int userId,String isbn, String title,LocalDate borrowedDate ) {
        this.userId = userId;
        this.borrowedDate = borrowedDate;
        this.isbn = isbn;
        this.title = title;
    }
    
}
