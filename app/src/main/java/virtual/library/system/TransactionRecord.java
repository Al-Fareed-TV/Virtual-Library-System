package virtual.library.system;
import java.time.LocalDate;
public class TransactionRecord {
    private String userId;
    private LocalDate borrowedDate;
    private String isbn;
    public TransactionRecord() {
    }
    public String getUserId() {
        return userId;
    }
    public LocalDate getBorrowingDate() {
        return borrowedDate;
    }
    public String getIsbn() {
        return isbn;
    }
    public TransactionRecord(String userId,String isbn, LocalDate borrowedDate ) {
        this.userId = userId;
        this.borrowedDate = borrowedDate;
        this.isbn = isbn;
    }
    
    
}
