package virtual.library.system;
import java.time.LocalDate;
public class TransactionRecord {
    private int userId;
    private LocalDate borrowedDate;
    private String isbn;
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
    public TransactionRecord(int userId,String isbn, LocalDate borrowedDate ) {
        this.userId = userId;
        this.borrowedDate = borrowedDate;
        this.isbn = isbn;
    }
    
    
}
